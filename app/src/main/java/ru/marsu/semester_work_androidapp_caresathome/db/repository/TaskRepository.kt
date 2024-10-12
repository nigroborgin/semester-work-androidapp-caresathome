package ru.marsu.semester_work_androidapp_caresathome.db.repository


import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import ru.marsu.semester_work_androidapp_caresathome.db.converter.PeriodicityConverter
import ru.marsu.semester_work_androidapp_caresathome.db.converter.StatusConverter
import ru.marsu.semester_work_androidapp_caresathome.db.converter.TaskConverter
import ru.marsu.semester_work_androidapp_caresathome.db.dao.PeriodicityDao
import ru.marsu.semester_work_androidapp_caresathome.db.dao.StatusDao
import ru.marsu.semester_work_androidapp_caresathome.db.dao.TaskDao
import ru.marsu.semester_work_androidapp_caresathome.db.entity.Task
import ru.marsu.semester_work_androidapp_caresathome.dto.StatusDto
import ru.marsu.semester_work_androidapp_caresathome.dto.TaskDto
import java.util.ArrayList

class TaskRepository(
    private val taskDao: TaskDao,
    private val statusDao: StatusDao,
    private val periodicityDao: PeriodicityDao) {

    /**
     * @return `id` of created `Task`
     */
    fun create(taskDto: TaskDto): Long = runBlocking {
        val idCreated = async {
            return@async taskDao.insert(TaskConverter.instance.convert(taskDto))
        }.await()

        return@runBlocking idCreated
    }

    fun getOneById(id: Int): TaskDto = runBlocking {
        val taskModel = async {
            val taskDto: TaskDto = TaskConverter.instance.convert(
                taskDao.getOneById(id)
            )
            taskDto.status = StatusConverter.instance.convert(
                statusDao.getOneById(taskDto.status?.id!!)
            )
            taskDto.periodicity = PeriodicityConverter.instance.convert(
                periodicityDao.getOneById(taskDto.periodicity?.id!!)
            )
            return@async taskDto
        }.await()

        return@runBlocking taskModel
    }

    fun getAll(): List<TaskDto> = runBlocking {
        val taskModelList = async {
            val taskList: List<Task> = taskDao.getAll()
            val taskDtoList = ArrayList<TaskDto>(taskList.size)
            for (task: Task in taskList) {
                val taskModel = TaskConverter.instance.convert(task)
                taskModel.status = StatusConverter.instance.convert(
                    statusDao.getOneById(task.statusId!!)
                )
                taskModel.periodicity = PeriodicityConverter.instance.convert(
                    periodicityDao.getOneById(task.periodicityId!!)
                )
                taskDtoList.add(taskModel)
            }
            return@async taskDtoList
        }.await()

        return@runBlocking taskModelList
    }

    fun getByStatus(statusDto: StatusDto): List<TaskDto> = runBlocking {
        val taskModelList = async {
            val taskList: List<Task> = taskDao.getByStatus(statusDto.id!!)
            val taskDtoList = ArrayList<TaskDto>(taskList.size)
            for (task: Task in taskList) {
                val taskModel = TaskConverter.instance.convert(task)
                taskModel.status = StatusConverter.instance.convert(
                    statusDao.getOneById(task.statusId!!)
                )
                taskModel.periodicity = PeriodicityConverter.instance.convert(
                    periodicityDao.getOneById(task.periodicityId!!)
                )
                taskDtoList.add(taskModel)
            }
            return@async taskDtoList
        }.await()

        return@runBlocking taskModelList
    }

    /**
     * @return `count` of updated `Task`
     */
    fun update(taskDto: TaskDto): Int = runBlocking {
        val countUpdated = async {
            return@async taskDao.update(TaskConverter.instance.convert(taskDto))
        }.await()

        return@runBlocking countUpdated
    }

    /**
     * @return `count` of deleted `Task`
     */
    fun delete(taskDto: TaskDto): Int = runBlocking {
        val countDeleted = async {
            return@async taskDao.delete(TaskConverter.instance.convert(taskDto))
        }.await()

        return@runBlocking countDeleted
    }

}
