package ru.mai.dep806.student.works.api.repository

import ru.mai.dep806.student.works.api.model.PersistentStudent
import ru.mai.dep806.student.works.api.model.StudentToUpdate

interface StudentSearchRepository {
    fun add(student: PersistentStudent)
    fun update(id: String, student: StudentToUpdate)
    fun delete(id: String)
    fun find(filter: String): List<PersistentStudent>
}