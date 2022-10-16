package ru.mai.dep806.student.works.api.repository

import ru.mai.dep806.student.works.api.model.PersistentStudent

interface StudentReadRepository {
    fun findAll(): List<PersistentStudent>
    fun findById(id: String): PersistentStudent?
}