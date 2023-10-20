package ru.mai.dep806.student.works.api.service

import com.hazelcast.core.HazelcastInstance
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.mai.dep806.student.works.api.model.Student
import ru.mai.dep806.student.works.api.model.StudentToUpdate
import ru.mai.dep806.student.works.api.model.toPersistence
import ru.mai.dep806.student.works.api.model.toStudent
import ru.mai.dep806.student.works.api.repository.StudentReadRepository
import ru.mai.dep806.student.works.api.repository.StudentSearchRepository
import ru.mai.dep806.student.works.api.repository.StudentWriteRepository

@Service
class StudentServiceImpl(
    private val studentReadRepository: StudentReadRepository,
    private val studentWriteRepository: StudentWriteRepository,
    private val studentSearchRepository: StudentSearchRepository,
    private val hazelcastInstance: HazelcastInstance
) : StudentService {
    private val logger = LoggerFactory.getLogger(StudentServiceImpl::class.java)

    override fun findAll(): List<Student> = studentReadRepository
        .findAll()
        .map { it.toStudent() }

    override fun find(searchFilter: String): List<Student> = studentSearchRepository
        .find(searchFilter)
        .map { it.toStudent() }

    override fun findById(id: String): Student?{
        val studentsCache = hazelcastInstance.getMap<String, Student>("students")
        logger.info("Trying to get student with id = $id from cache...")

        if (studentsCache.containsKey(id)){
            logger.info("Student with id = $id exist in cache...")
            return studentsCache[id]
        }

        logger.info("Student with id = $id didn't exist in cache...")

        val student = studentReadRepository
            .findById(id)
            ?.toStudent()

        if(student != null){
            studentsCache.put(id, student)
        }

        return student
    }

    override fun add(student: StudentToUpdate) {
        val studentId = this.studentWriteRepository.add(student.toPersistence())
        studentSearchRepository.add(student.toPersistence(studentId))
    }

    override fun update(id: String, student: StudentToUpdate): Student? {
        studentWriteRepository.update(id, student)
        studentSearchRepository.update(id, student)
        return findById(id)
    }

    override fun delete(id: String) {
        studentWriteRepository.delete(id)
        studentSearchRepository.delete(id)
    }
}