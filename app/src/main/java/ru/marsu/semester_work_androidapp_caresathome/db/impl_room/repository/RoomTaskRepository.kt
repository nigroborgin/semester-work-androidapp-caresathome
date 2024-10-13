package ru.marsu.semester_work_androidapp_caresathome.db.impl_room.repository


import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.converter.PeriodicityConverter
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.converter.StatusConverter
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.converter.TaskConverter
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.dao.StatusDao
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.dao.TaskDao
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.entity.Task
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.dao.PeriodicityDao
import ru.marsu.semester_work_androidapp_caresathome.db.repository.TaskRepository
import ru.marsu.semester_work_androidapp_caresathome.dto.StatusDto
import ru.marsu.semester_work_androidapp_caresathome.dto.TaskDto
import java.util.ArrayList

class RoomTaskRepository(
    private val taskDao: TaskDao,
    private val statusDao: StatusDao,
    private val periodicityDao: PeriodicityDao
) : TaskRepository {

    override fun create(taskDto: TaskDto): Int = runBlocking {
        val idCreated = async {
            return@async taskDao.insert(TaskConverter.instance.convert(taskDto))
        }.await()

        return@runBlocking idCreated.toInt()
    }

    override fun getOneById(id: Int): TaskDto = runBlocking {
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

    override fun getAll(): List<TaskDto> = runBlocking {
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

    override fun getByStatus(statusDto: StatusDto): List<TaskDto> = runBlocking {
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

    override fun update(taskDto: TaskDto): Int = runBlocking {
        val countUpdated = async {
            return@async taskDao.update(TaskConverter.instance.convert(taskDto))
        }.await()

        return@runBlocking countUpdated
    }

    override fun delete(taskDto: TaskDto): Int = runBlocking {
        val countDeleted = async {
            return@async taskDao.delete(TaskConverter.instance.convert(taskDto))
        }.await()

        return@runBlocking countDeleted
    }

}
