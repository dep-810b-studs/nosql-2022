package ru.mai.dep806.student.works.api.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component
import ru.mai.dep806.student.works.api.model.StudentToUpdate
import ru.mai.dep806.student.works.api.repository.MigrationRepository
import java.io.File
import java.net.URL
import kotlin.streams.asSequence

@Component
class StudentsImporter(private val studentService: StudentService,
                       private val migrationRepository: MigrationRepository): InitializingBean {
    private val logger = LoggerFactory.getLogger(StudentsImporter::class.java)

    companion object{
        private const val studentsPath = "database/students"
    }

    override fun afterPropertiesSet() {
        if(getResourceFolderFiles(studentsPath).isNullOrEmpty()){
            return
        }
        getResourceFolderFiles(studentsPath)
            ?.forEach {
                if(it != null){
                    importFromFile("$studentsPath/${it!!.name}")
                }
            }
    }

    private fun importFromFile(fileName: String){
        if(migrationRepository.isFileImported(fileName))
            return

        logger.info("Starting import students from file $fileName...")
        parseStudentsFromCsv(fileName)
            .forEach {
                studentService.add(it)
            }
        migrationRepository.addImportedFile(fileName)
        logger.info("Import students from file $fileName finished...")
    }

    private fun parseStudentsFromCsv(studentsFile: String): Sequence<StudentToUpdate>{
        val inputStream = {}.javaClass.classLoader.getResourceAsStream(studentsFile)
        val reader = inputStream.bufferedReader()
        reader.readLine()
        return reader.lines()
            .parallel()
            .filter { it.isNotBlank() }
            .map {
                val splittedLine = it.split(';', ignoreCase = false)
                val studentAge = (15 .. 50).random()
                StudentToUpdate(splittedLine[1], studentAge)
            }
            .asSequence()
    }

    private fun getResourceFolderFiles(folder: String): Array<File?>? {
        val loader = {}.javaClass.classLoader
        val url: URL = loader.getResource(folder) ?: return null
        val path: String = url.path
        return File(path).listFiles()
    }
}