package ru.mai.dep806.student.works.api.repository

interface UnitOfWork {
    val studentWriteRepository: StudentWriteRepository

    fun commit()
    fun rollback()
}

interface UnitOfWorkFactory {
    fun createInstance(): UnitOfWork
}