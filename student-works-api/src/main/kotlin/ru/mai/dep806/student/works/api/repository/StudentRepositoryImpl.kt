package ru.mai.dep806.student.works.api.repository

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import ru.mai.dep806.student.works.api.model.PersistentStudent

@Repository("studentRepository")
class StudentRepositoryImpl(
    val mongoOperations: MongoOperations
) : StudentRepository {

    override fun findAll(): List<PersistentStudent> =
        mongoOperations.findAll(PersistentStudent::class.java)

    override fun findById(id: String): PersistentStudent? {
        val students = mongoOperations.find(Query.query(Criteria.where("_id").`is`(id)), PersistentStudent::class.java)
        return students.first()
    }

    override fun add(student: PersistentStudent) {
        mongoOperations.save(student)
    }

    override fun update(student: PersistentStudent): PersistentStudent? {
        mongoOperations.save(student)
        return findById(student.id!!)
    }

    override fun delete(id: String) {
        mongoOperations.remove(Query.query(Criteria.where("_id").`is`(id)), PersistentStudent::class.java)
    }
}