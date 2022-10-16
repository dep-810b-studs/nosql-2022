package ru.mai.dep806.student.works.api.service

import ru.mai.dep806.student.works.api.model.Student
import ru.mai.dep806.student.works.api.model.StudentToUpdate

interface StudentService {
    fun findAll(): List<Student>
    fun find(searchFilter: String): List<Student>
    fun findById(id: String): Student?
    fun add(student: Student)
    fun update(id: String, student: StudentToUpdate) : Student?
    fun delete(id: String)
}