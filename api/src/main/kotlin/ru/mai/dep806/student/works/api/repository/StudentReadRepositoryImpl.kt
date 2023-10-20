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

    companion object {
        const val DOCUMENT_NAME = "student"
    }

    override fun findAll(): List<PersistentStudent> {
        val students = mongoOperations
            .execute {
                it
                    .getCollection(DOCUMENT_NAME)
                    .find()
            }
            ?.map { PersistentStudent(it.getObjectId("_id").toString(), it.getString("name"), it.getInteger("age")) }

        return students?.mapNotNull { it } ?: emptyList()
    }

    override fun findById(id: String): PersistentStudent? = mongoOperations.findById(id, PersistentStudent::class.java)
}