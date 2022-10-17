package ru.mai.dep806.student.works.api.repository

import ru.mai.dep806.student.works.api.model.PersistentStudent

interface StudentSearchRepository {
    fun add(student: PersistentStudent)
    fun update(student: PersistentStudent)
    fun delete(id: Int)
    fun find(filter: String): List<PersistentStudent>
}