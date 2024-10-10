package ru.marsu.semester_work_androidapp_caresathome.db.dao

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.util.Log
import ru.marsu.semester_work_androidapp_caresathome.db.DbScheme
import ru.marsu.semester_work_androidapp_caresathome.entity.Status

class StatusDao(
    private val db: SQLiteDatabase) {

    val TAG = "MY_DEBUG"

    fun getOneById(id: Int): Status {

        val cursor = db.query(
            DbScheme.Status.TABLE_NAME,
            null,
            "${BaseColumns._ID} = $id",
            null,
            null,
            null,
            null
        )

        lateinit var status: Status
        if (cursor.moveToFirst()) {
            status = makeEntity(cursor)
            cursor.close()
        }

        Log.d(TAG, status.toString())
        return status
    }

    private fun makeEntity(cursor: Cursor): Status {

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(
            BaseColumns._ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(
            DbScheme.Status.COLUMN_NAME_TITLE))

        val status = Status(
            id,
            title
        )

        return status
    }
}
