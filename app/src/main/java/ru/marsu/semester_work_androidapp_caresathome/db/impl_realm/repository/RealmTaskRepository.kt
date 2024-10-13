package ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.repository

import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.dao.PeriodicityDao
import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.dao.StatusDao
import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.dao.TaskDao
import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.converter.PeriodicityConverter
import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.converter.StatusConverter
import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.converter.TaskConverter
import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.entity.Task
import ru.marsu.semester_work_androidapp_caresathome.db.repository.TaskRepository
import ru.marsu.semester_work_androidapp_caresathome.dto.StatusDto
import ru.marsu.semester_work_androidapp_caresathome.dto.TaskDto
import java.util.ArrayList

class RealmTaskRepository(
    private val taskDao: TaskDao,
    private val statusDao: StatusDao,
    private val periodicityDao: PeriodicityDao
) : TaskRepository {

    override fun create(taskDto: TaskDto): Int {
        val idCreated = taskDao.insert(TaskConverter.instance.convert(taskDto))
        return idCreated
    }

    override fun getOneById(id: Int): TaskDto {
        val taskDto: TaskDto = TaskConverter.instance.convert(
            taskDao.getOneById(id)
        )
        taskDto.status = StatusConverter.instance.convert(
            statusDao.getOneById(taskDto.status?.id!!)
        )
        taskDto.periodicity = PeriodicityConverter.instance.convert(
            periodicityDao.getOneById(taskDto.periodicity?.id!!)
        )
        return taskDto
    }

    override fun getAll(): List<TaskDto> {
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
        return taskDtoList
    }

    override fun getByStatus(statusDto: StatusDto): List<TaskDto> {
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
        return taskDtoList
    }

    override fun update(taskDto: TaskDto): Int {
        val countUpdated = taskDao.update(TaskConverter.instance.convert(taskDto))
        return countUpdated
    }

    override fun delete(taskDto: TaskDto): Int {
        val countDeleted = taskDao.delete(TaskConverter.instance.convert(taskDto))
        return countDeleted
    }

}
