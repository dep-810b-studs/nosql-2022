package ru.mai.dep806.student.works.api.repository

import org.springframework.stereotype.Repository
import ru.mai.dep806.student.works.api.model.PersistentStudent

@Repository("inMemoryPersistentStudentRepository")
class InMemoryPersistentStudentRepositoryImpl : StudentReadRepository, StudentWriteRepository {

    private val students: MutableList<PersistentStudent> = mutableListOf(
        PersistentStudent(1,"Иван",21),
        PersistentStudent(2,"Алексей",22),
        PersistentStudent(3,"Сергей",23),
    )

    override fun findAll(): List<PersistentStudent> = students

    override fun findById(id: String): PersistentStudent? = students.singleOrNull { it.id == id.toInt() }

    override fun add(student: PersistentStudent) {

        if(!students.any { it.id == student.id }){
            students.add(student)
        }

    }

    override fun update(persistentStudent: PersistentStudent) : PersistentStudent? {

        val student = findById(persistentStudent.id.toString())

        student?.also {

            persistentStudent.name.also {
                if(student.name != it){
                    student.name = it
                }
            }

            persistentStudent.age.also {
                if(student.age != it){
                    student.age = it
                }
            }

        }

        return student
    }

    override fun delete(id: String) {
        students.removeIf { it.id == id.toInt() }
    }
}