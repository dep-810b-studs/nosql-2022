package ru.mai.dep806.student.works.api.repository

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import ru.mai.dep806.student.works.api.model.PersistentStudent

class StudentWriteRepositoryImpl(private val mongoOperations: MongoOperations,
                                 ): StudentWriteRepository {

    override fun add(student: PersistentStudent) {
        mongoOperations.save(student)
    }

    override fun update(student: PersistentStudent): PersistentStudent? {
        val updateDefinition = Update()
            .set("name", student.name)
            .set("age", student.age)

        mongoOperations.upsert(whereQuery(student.id!!), updateDefinition, PersistentStudent::class.java)

        return mongoOperations
            .find(whereQuery(student.id!!), PersistentStudent::class.java)
            .first()
    }

    override fun delete(id: Int) {
        mongoOperations.remove(whereQuery(id), PersistentStudent::class.java)
    }

    private fun whereQuery(id: Int): Query = Query.query(Criteria.where("_id").`is`(id))
}