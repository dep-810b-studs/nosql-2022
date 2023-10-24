package ru.mai.dep806.student.works.api.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.mai.dep806.student.works.api.repository.StudentReadRepositoryImpl
import java.io.Serializable

data class StudentToUpdate(
    val name: String?,
    val age: Int?
)

data class Student(
    val id: String,
    val name: String,
    val age: Int
) : Serializable

@Document(StudentReadRepositoryImpl.DOCUMENT_NAME)
data class PersistentStudent(
    @Id
    val id: String? = null,
    val name: String?,
    val age: Int?
)

fun PersistentStudent.toStudent(): Student = Student(
    this.id!!,
    this.name!!,
    this.age!!
)

fun StudentToUpdate.toPersistence(id: String? = null): PersistentStudent = PersistentStudent(
    id = id,
    name = this.name,
    age = this.age
)