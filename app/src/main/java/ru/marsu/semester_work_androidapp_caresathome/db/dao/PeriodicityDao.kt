package ru.marsu.semester_work_androidapp_caresathome.db.dao

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import ru.marsu.semester_work_androidapp_caresathome.db.DbScheme
import ru.marsu.semester_work_androidapp_caresathome.entity.Periodicity

class PeriodicityDao(
    private val db: SQLiteDatabase) {

    fun getOneById(id: Int): Periodicity {

        val cursor = db.query(
            DbScheme.Periodicity.TABLE_NAME,
            null,
            "${BaseColumns._ID} = $id",
            null,
            null,
            null,
            null
        )

        lateinit var periodicity: Periodicity
        if (cursor.moveToFirst()) {
            periodicity = makeEntity(cursor)
            cursor.close()
        }

        return periodicity
    }

    fun getAll(): List<Periodicity> {

        val periodicityList = ArrayList<Periodicity>()
        val cursor = db.query(DbScheme.Task.TABLE_NAME, null, null, null, null, null, null)

        while (cursor?.moveToNext()!!) {
            val periodicity = makeEntity(cursor)
            periodicityList.add(periodicity)
        }
        cursor.close()

        return periodicityList
    }

    private fun makeEntity(cursor: Cursor): Periodicity {

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(
            BaseColumns._ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(
            DbScheme.Periodicity.COLUMN_NAME_TITLE))

        return Periodicity(id, title)
    }

}
