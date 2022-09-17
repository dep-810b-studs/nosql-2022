package ru.mai.dep806.student.works.api.repository

import ru.mai.dep806.student.works.api.model.PersistentStudent

interface StudentRepository {
    fun findAll(): List<PersistentStudent>
    fun findById(id: String): PersistentStudent?
    fun add(student: PersistentStudent)
    fun update(student: PersistentStudent) : PersistentStudent?
    fun delete(id: String)
}