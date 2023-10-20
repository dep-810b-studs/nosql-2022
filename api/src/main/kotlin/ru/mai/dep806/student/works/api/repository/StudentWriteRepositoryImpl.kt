package ru.mai.dep806.student.works.api.repository

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository
import ru.mai.dep806.student.works.api.model.PersistentStudent
import ru.mai.dep806.student.works.api.model.StudentToUpdate

@Repository("studentWriteRepository")
class StudentWriteRepositoryImpl(private val mongoOperations: MongoOperations) : StudentWriteRepository {

    override fun add(student: PersistentStudent): String {
        val addedStudent = mongoOperations.save(student)
        return addedStudent.id ?: ""
    }

    override fun update(id: String, student: StudentToUpdate): PersistentStudent? {
        val updateDefinition = Update()
            .set("name", student.name)
            .set("age", student.age)

        mongoOperations.upsert(whereQuery(id), updateDefinition, PersistentStudent::class.java)

        return mongoOperations
            .find(whereQuery(id), PersistentStudent::class.java)
            .first()
    }

    override fun delete(id: String) {
        mongoOperations.remove(whereQuery(id), PersistentStudent::class.java)
    }

    private fun whereQuery(id: String): Query = Query.query(Criteria.where("_id").`is`(id))
}