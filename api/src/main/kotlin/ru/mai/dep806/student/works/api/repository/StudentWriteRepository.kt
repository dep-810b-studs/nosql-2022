package ru.mai.dep806.student.works.api.repository

import ru.mai.dep806.student.works.api.model.PersistentStudent
import ru.mai.dep806.student.works.api.model.StudentToUpdate

interface StudentWriteRepository {
    fun add(student: PersistentStudent): String
    fun update(id: String, student: StudentToUpdate): PersistentStudent?
    fun delete(id: String)
}