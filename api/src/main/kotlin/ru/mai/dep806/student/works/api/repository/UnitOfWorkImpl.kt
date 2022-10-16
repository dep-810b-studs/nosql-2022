package ru.mai.dep806.student.works.api.repository

import com.mongodb.client.ClientSession

class UnitOfWorkImpl(override val studentWriteRepository: StudentWriteRepository,
                     private val session: ClientSession,) : UnitOfWork {
    override fun commit() {
        session.commitTransaction()
    }

    override fun rollback() {
        session.abortTransaction()
    }
}