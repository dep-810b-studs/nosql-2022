package ru.mai.dep806.student.works.api.controller

import org.springframework.web.bind.annotation.*
import ru.mai.dep806.student.works.api.model.*
import ru.mai.dep806.student.works.api.service.StudentService

@RestController
class StudentController(
    private val studentService: StudentService) {
    @GetMapping("/api/students/all")
    fun getAllStudents(): List<Student> = studentService.findAll()
    @GetMapping("/api/students/{id}")
    fun getStudent(@PathVariable id: String): Student? = studentService.findById(id)
    @GetMapping("/api/students")
    fun getFilteredStudent(@RequestParam searchFilter: String): List<Student> = studentService.find(searchFilter)
    @PostMapping("/api/students")
    fun addStudent(@RequestBody student: StudentToUpdate) = studentService.add(student)
    @PutMapping("/api/students/{id}")
    fun updateStudent(@PathVariable id: String, @RequestBody student: StudentToUpdate): Student? = studentService.update(id, student)
    @DeleteMapping("/api/students/{id}")
    fun deleteStudent(@PathVariable id: String) = studentService.delete(id)
}