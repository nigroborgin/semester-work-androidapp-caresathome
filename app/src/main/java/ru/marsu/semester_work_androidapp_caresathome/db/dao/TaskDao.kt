package ru.marsu.semester_work_androidapp_caresathome.db.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import ru.marsu.semester_work_androidapp_caresathome.db.DbScheme
import ru.marsu.semester_work_androidapp_caresathome.entity.Periodicity
import ru.marsu.semester_work_androidapp_caresathome.entity.Status
import ru.marsu.semester_work_androidapp_caresathome.entity.Task
import java.time.LocalDateTime

class TaskDao(
    private val db: SQLiteDatabase) {

    fun insert(task: Task): Long {

        val values = getValues(task)
        val idInserted = db.insert(DbScheme.Task.TABLE_NAME, null, values)
        return idInserted
    }



    fun getOneById(id: Int): Task {

        val cursor = db.query(
            DbScheme.Task.TABLE_NAME,
            null,
            "${BaseColumns._ID} = $id",
            null,
            null,
            null,
            null
        )

        lateinit var task: Task

        if (cursor.moveToFirst()) {
            task = makeEntity(cursor)
            cursor.close()
        }

        return task
    }

    fun getAll(): List<Task> {

        val taskList = ArrayList<Task>()
        val cursor = db.query(DbScheme.Task.TABLE_NAME, null, null, null, null, null, null)

        while (cursor?.moveToNext()!!) {
            val task = makeEntity(cursor)
            taskList.add(task)
        }
        cursor.close()

        return taskList
    }

    fun getAllByStatus(statusId: Int): List<Task> {
        val taskList = ArrayList<Task>()
        val cursor = db.query(DbScheme.Task.TABLE_NAME, null, DbScheme.Task.COLUMN_NAME_STATUS_ID + " = " + statusId.toString(), null, null, null, null)

        while (cursor?.moveToNext()!!) {
            val task = makeEntity(cursor)
            taskList.add(task)
        }
        cursor.close()

        return taskList
    }

    fun update(task : Task) : Int {

        val values = getValues(task)
        val whereClause = "${BaseColumns._ID} = ${task.id}"
        return db.update(DbScheme.Task.TABLE_NAME, values, whereClause, null)
    }

    fun delete(id : Int) {

        val whereClause = "${BaseColumns._ID} = $id"
        db.delete(DbScheme.Task.TABLE_NAME, whereClause, null)
    }

    private fun makeEntity(cursor: Cursor): Task {

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(
            BaseColumns._ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(
            DbScheme.Task.COLUMN_NAME_TITLE))
        val dueDateTimeString = cursor.getString(cursor.getColumnIndexOrThrow(
            DbScheme.Task.COLUMN_NAME_DUE_DATE_TIME))
        val timeWitchCompletedString = cursor.getString(cursor.getColumnIndexOrThrow(
            DbScheme.Task.COLUMN_NAME_TIME_WITCH_COMPLETED))
        val periodicityId = cursor.getInt(cursor.getColumnIndexOrThrow(
            DbScheme.Task.COLUMN_NAME_PERIODICITY_ID))
        val statusId = cursor.getInt(cursor.getColumnIndexOrThrow(
            DbScheme.Task.COLUMN_NAME_STATUS_ID))
        val patient = cursor.getString(cursor.getColumnIndexOrThrow(
            DbScheme.Task.COLUMN_NAME_PATIENT))
        val bloodPressure = cursor.getString(cursor.getColumnIndexOrThrow(
            DbScheme.Task.COLUMN_NAME_BLOOD_PRESSURE))
        val heartRate = cursor.getInt(cursor.getColumnIndexOrThrow(
            DbScheme.Task.COLUMN_NAME_HEART_RATE))
        val bloodSampleCollection = cursor.getInt(cursor.getColumnIndexOrThrow(
            DbScheme.Task.COLUMN_NAME_BLOOD_SAMPLE_COLLECTION))

        val task = Task(
            id,
            title,
            LocalDateTime.parse(dueDateTimeString),
            if (!timeWitchCompletedString.equals("null")) LocalDateTime.parse(timeWitchCompletedString) else null,
            Periodicity(periodicityId),
            Status(statusId),
            patient,
            bloodPressure,
            heartRate,
            bloodSampleCollection == 1
        )

        return task
    }

    private fun getValues(task : Task) : ContentValues {

        return ContentValues().apply {
            put(DbScheme.Task.COLUMN_NAME_TITLE, task.title)
            put(DbScheme.Task.COLUMN_NAME_DUE_DATE_TIME, task.dueDateTime.toString())
            put(DbScheme.Task.COLUMN_NAME_TIME_WITCH_COMPLETED, task.timeWitchCompleted.toString())
            put(DbScheme.Task.COLUMN_NAME_PERIODICITY_ID, task.periodicity?.id)
            put(DbScheme.Task.COLUMN_NAME_STATUS_ID, task.status?.id)
            put(DbScheme.Task.COLUMN_NAME_PATIENT, task.patient)
            put(DbScheme.Task.COLUMN_NAME_BLOOD_PRESSURE, task.bloodPressure)
            put(DbScheme.Task.COLUMN_NAME_HEART_RATE, task.heartRate)
            put(DbScheme.Task.COLUMN_NAME_BLOOD_SAMPLE_COLLECTION, if (task.isBloodSampleCollection == true) 1 else 0)
        }
    }

}
