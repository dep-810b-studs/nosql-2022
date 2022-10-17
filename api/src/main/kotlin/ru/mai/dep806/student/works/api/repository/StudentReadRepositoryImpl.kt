package ru.mai.dep806.student.works.api.repository

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import ru.mai.dep806.student.works.api.model.PersistentStudent

@Repository("studentReadRepository")
class StudentReadRepositoryImpl(
    val mongoOperations: MongoOperations,
) : StudentReadRepository {

    override fun findAll(): List<PersistentStudent> =
        mongoOperations.findAll(PersistentStudent::class.java)

    override fun findById(id: Int): PersistentStudent? {
        val students = mongoOperations.find(Query.query(Criteria.where("_id").`is`(id)), PersistentStudent::class.java)
        return students.first()
    }
}