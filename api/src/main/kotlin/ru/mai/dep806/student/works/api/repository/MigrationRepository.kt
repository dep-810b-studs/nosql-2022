package ru.mai.dep806.student.works.api.repository

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters
import org.bson.Document
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.stereotype.Repository

interface MigrationRepository {
    fun isFileImported(fileName: String): Boolean
    fun addImportedFile(fileName: String)
}

@Repository
class MigrationRepositoryImpl(private val mongoOperations: MongoOperations) : MigrationRepository {
    private val migrations: MongoCollection<Document>
        get() = mongoOperations.getCollection("migrations")


    override fun isFileImported(fileName: String): Boolean {
        val usersMigrations = migrations.find(Filters.eq("file", fileName))
        return usersMigrations.any()
    }

    override fun addImportedFile(fileName: String) {
        val document = Document("file",fileName)
        migrations.insertOne(document)
    }
}