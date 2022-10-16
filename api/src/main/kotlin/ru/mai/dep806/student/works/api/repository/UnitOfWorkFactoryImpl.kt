package ru.mai.dep806.student.works.api.repository

import com.mongodb.client.MongoClient
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.stereotype.Service

@Service
class UnitOfWorkFactoryImpl(private val mongoOperations: MongoOperations,
                            private val mongoClient: MongoClient): UnitOfWorkFactory {
    override fun createInstance(): UnitOfWork{
        val session = mongoClient.startSession()
        session.startTransaction()
        val mongoOperationsWithSession = mongoOperations.withSession(session)
        return UnitOfWorkImpl(StudentWriteRepositoryImpl(mongoOperationsWithSession), session)
    }
}