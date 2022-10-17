package ru.mai.dep806.student.works.api.repository

import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import ru.mai.dep806.student.works.api.model.PersistentStudent

class StudentWriteRepositoryImpl(private val mongoOperations: MongoOperations,
                                 ): StudentWriteRepository {

    override fun add(student: PersistentStudent) {
        mongoOperations.save(student)
    }

    override fun update(student: PersistentStudent): PersistentStudent? {
        mongoOperations.save(student)

        return mongoOperations
            .find(Query.query(Criteria.where("_id").`is`(student.id)), PersistentStudent::class.java)
            .first()
    }

    override fun delete(id: Int) {
        mongoOperations.remove(Query.query(Criteria.where("_id").`is`(id)), PersistentStudent::class.java)
    }
}