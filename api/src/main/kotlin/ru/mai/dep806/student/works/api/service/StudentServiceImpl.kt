package ru.mai.dep806.student.works.api.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.mai.dep806.student.works.api.model.Student
import ru.mai.dep806.student.works.api.model.StudentToUpdate
import ru.mai.dep806.student.works.api.model.toPersistence
import ru.mai.dep806.student.works.api.model.toStudent
import ru.mai.dep806.student.works.api.repository.StudentReadRepository
import ru.mai.dep806.student.works.api.repository.StudentSearchRepository
import ru.mai.dep806.student.works.api.repository.UnitOfWorkFactory

@Service
class StudentServiceImpl(
    private val studentReadRepository: StudentReadRepository,
    private val studentSearchRepository: StudentSearchRepository,
    private val unitOfWorkFactory: UnitOfWorkFactory
    ): StudentService {

    private val logger = LoggerFactory.getLogger(StudentServiceImpl::class.java)


    override fun findAll(): List<Student> = studentReadRepository
        .findAll()
        .map { it.toStudent() }

    override fun find(searchFilter: String): List<Student> = studentSearchRepository
        .find(searchFilter)
        .map { it.toStudent() }

    override fun findById(id: String): Student? = studentReadRepository
        .findById(id.toInt())
        ?.toStudent()

    override fun add(student: StudentToUpdate) {
        with(unitOfWorkFactory.createInstance()) {

            var studentId = ""

            try {
                studentId = this.studentWriteRepository.add(student.toPersistence())
            }
            catch(exception: Exception){
                logger.error("Some error during saving to mongo: $exception")
                return
            }

            if (studentId == ""){
                logger.error("Can't get mongo entity id")
                this.rollback()
                return
            }

            try {
                studentSearchRepository.add(student.toPersistence(studentId))
            }
            catch(exception: Throwable){
                logger.error("Some error during saving to elasticsearch: $exception")
                logger.warn("Rollback mongo changes...")
                this.rollback()
                return
            }

            this.commit()
        }
    }

    override fun update(id: String, student: StudentToUpdate): Student? {
        with(unitOfWorkFactory.createInstance()) {
            try {
                this.studentWriteRepository.update(id, student)
            }
            catch(exception: Exception){
                logger.error("Some error during saving to mongo: $exception")
                return null
            }

            try {
                studentSearchRepository.update(id, student)
            }
            catch(exception: Exception){
                logger.error("Some error during saving to elasticsearch: $exception")
                logger.warn("Rollback mongo changes...")
                this.rollback()
                return null
            }

            this.commit()

            return findById(id)
        }
    }

    override fun delete(id: String) {
        with(unitOfWorkFactory.createInstance()) {
            try {
                this.studentWriteRepository.delete(id)
            }
            catch(exception: Exception){
                logger.error("Some error during deletion from mongo: $exception")
                return
            }

            try {
                studentSearchRepository.delete(id)
            }
            catch(exception: Exception){
                logger.error("Some error during deletion from elasticsearch: $exception")
                logger.warn("Rollback mongo changes...")
                this.rollback()
                return
            }

            this.commit()
        }
    }
}