package ru.mai.dep806.student.works.api.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

data class Student(
    var id: String?,
    var name: String?,
    var age: Int?
)

@Document("student")
data class PersistentStudent(
    @Id
    var id: String?,
    var name: String?,
    var age: Int?
)

data class StudentToUpdate(
    var name: String?,
    var age: Int?
)

fun Student.toPersistence(): PersistentStudent = PersistentStudent(
    this.id,
    this.name,
    this.age
)

fun PersistentStudent.toStudent(): Student = Student(
    this.id,
    this.name,
    this.age
)

fun StudentToUpdate.toPersistence(id: String): PersistentStudent = PersistentStudent(
    id,
    this.name,
    this.age
)