package ru.mai.dep806.student.works.api.controller

import org.springframework.web.bind.annotation.*
import ru.mai.dep806.student.works.api.model.Student
import ru.mai.dep806.student.works.api.model.StudentToUpdate
import ru.mai.dep806.student.works.api.model.toPersistence
import ru.mai.dep806.student.works.api.model.toStudent
import ru.mai.dep806.student.works.api.repository.StudentRepository

@RestController
class StudentController(
    val studentRepository: StudentRepository
) {

    @GetMapping("/api/students")
    fun listStudents(): List<Student> = studentRepository
        .findAll()
        .map { it.toStudent() }


    @GetMapping("/api/students/{id}")
    fun getStudent(@PathVariable id: String): Student? = studentRepository
        .findById(id)
        ?.toStudent()

    @PostMapping("/api/students")
    fun addStudent(@RequestBody student: Student) = studentRepository.add(student.toPersistence())

    @PutMapping("/api/students/{id}")
    fun updateStudent(@PathVariable id: String, @RequestBody student: StudentToUpdate): Student? = studentRepository
        .update(student.toPersistence(id))
        ?.toStudent()

    @DeleteMapping("/api/students/{id}")
    fun deleteStudent(@PathVariable id: String) = studentRepository.delete(id)
}