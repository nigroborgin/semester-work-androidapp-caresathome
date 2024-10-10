package ru.marsu.semester_work_androidapp_caresathome.db.repository

import android.util.Log
import ru.marsu.semester_work_androidapp_caresathome.db.dao.PeriodicityDao
import ru.marsu.semester_work_androidapp_caresathome.db.dao.StatusDao
import ru.marsu.semester_work_androidapp_caresathome.db.dao.TaskDao
import ru.marsu.semester_work_androidapp_caresathome.entity.Status
import ru.marsu.semester_work_androidapp_caresathome.entity.Task

class TaskRepository(taskDao: TaskDao, statusDao: StatusDao, periodicityDao: PeriodicityDao) {

    private val taskDao = taskDao
    private val statusDao = statusDao
    private val periodicityDao = periodicityDao

    private val TAG = "MY_DEBUG"

    fun create(task : Task): Long {
        val idInserted = taskDao.insert(task)
        return idInserted
    }

    fun getOneById(id : Int) : Task {
        val task = taskDao.getOneById(id)
        Log.d(TAG, "TaskRepository.getOneById:\n$task")
        val status = task.status?.let { statusDao.getOneById(it.id) }
        Log.d(TAG, "TaskRepository.getOneById:\n$status")
        val periodicity = task.periodicity?.let { periodicityDao.getOneById(it.id) }
        Log.d(TAG, "TaskRepository.getOneById:\n$periodicity")

        task.status = status
        task.periodicity = periodicity

        return task
    }

    fun getAll(): List<Task> {

        val taskList = taskDao.getAll()

        for (task in taskList) {
            val status = task.status?.let { statusDao.getOneById(it.id) }
            val periodicity = task.periodicity?.let { periodicityDao.getOneById(it.id) }

            task.status = status
            task.periodicity = periodicity
        }

        return taskList
    }

    fun getAllByStatus(status: Status): List<Task> {
        val taskList = taskDao.getAllByStatus(status.id)

        for (task in taskList) {
            val status = task.status?.let { statusDao.getOneById(it.id) }
            val periodicity = task.periodicity?.let { periodicityDao.getOneById(it.id) }

            task.status = status
            task.periodicity = periodicity
        }

        return taskList

    }

    fun update(task : Task) : Int {
        return taskDao.update(task)
    }

    fun delete(task : Task) {
        task.id?.let { delete(it) }
    }

    fun delete(id : Int) {
        taskDao.delete(id)
    }

}
