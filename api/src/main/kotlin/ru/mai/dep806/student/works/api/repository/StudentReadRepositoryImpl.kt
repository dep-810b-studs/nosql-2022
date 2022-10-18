package ru.mai.dep806.student.works.api.repository

import com.mongodb.client.model.Sorts.ascending
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import ru.mai.dep806.student.works.api.model.PersistentStudent

@Repository("studentReadRepository")
class StudentReadRepositoryImpl(
    val mongoOperations: MongoOperations,
) : StudentReadRepository {

    companion object{
        const val DOCUMENT_NAME= "student"
    }

    override fun findAll(): List<PersistentStudent>{
        val students = mongoOperations
            .execute {
                it
                    .getCollection(DOCUMENT_NAME)
                    .find()
                    .sort(ascending("_id"))
            }
            ?.map { PersistentStudent(it.getInteger("_id"), it.getString("name"), it.getInteger("age")) }

        return students?.mapNotNull { it } ?: emptyList()
    }

    override fun findById(id: Int): PersistentStudent? {
        val students = mongoOperations.find(Query.query(Criteria.where("_id").`is`(id)), PersistentStudent::class.java)
        return students.first()
    }
}