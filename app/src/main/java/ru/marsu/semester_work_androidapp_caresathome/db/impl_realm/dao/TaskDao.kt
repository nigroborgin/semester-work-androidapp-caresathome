package ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.dao

import android.util.Log
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.entity.Task

class TaskDao(
    val db: Realm
) {

    fun getOneById(id: Int): Task {
        val task: Task = db.query<Task>("idForApp = $0", id).first().find()!!
        return task
    }

    fun getByStatus(statusId: Int): List<Task> {
        val taskList: List<Task> = db.query<Task>("statusId = $0", statusId)
            .find()
            .stream()
            .filter { it != null }
            .toList()
        return taskList
    }

    fun getAll(): List<Task> {
        val taskList: List<Task> = db.query<Task>()
            .find()
            .stream()
            .filter { it != null }
            .toList()
        return taskList
    }

    fun insert(task: Task): Int {
        val countTasks = db.query<Task>().find().count()
        if (countTasks == 0) {
            task.idForApp = 1
        } else {
            val foundTask = db.query<Task>()
                .sort("idForApp", Sort.DESCENDING)
                .find()
                .first()
            task.idForApp = foundTask.idForApp + 1
        }

        val managedTask = db.writeBlocking {
            db.query<Task>()
            return@writeBlocking copyToRealm(task, UpdatePolicy.ALL)
        }
        return managedTask.idForApp
    }

    fun update(task: Task): Int {
        val foundTask: Task = db.query<Task>("idForApp = $0", task.idForApp)
            .first()
            .find()!!

        db.writeBlocking {
            val findLatest = findLatest(foundTask)!!
            findLatest.title = task.title
            findLatest.dueDateTime = task.dueDateTime
            findLatest.timeWitchCompleted = task.timeWitchCompleted
            findLatest.periodicityId = task.periodicityId
            findLatest.statusId = task.statusId
            findLatest.patient = task.patient
            findLatest.bloodPressure = task.bloodPressure
            findLatest.heartRate = task.heartRate
            findLatest.isBloodSampleCollection = task.isBloodSampleCollection
        }
        return 1
    }

    fun delete(task: Task): Int {
        val foundTask = db.query<Task>("idForApp = $0", task.idForApp).first().find()
        if (foundTask != null) {
            db.writeBlocking {
                findLatest(foundTask)?.let { delete(it) }
            }
            return 1
        } else {
            return 0
        }
    }
}