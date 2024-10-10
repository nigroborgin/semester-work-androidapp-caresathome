package ru.marsu.semester_work_androidapp_caresathome.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DbManager(context: Context) {

    val dbHelper = DbHelper(context)
    lateinit var db: SQLiteDatabase

    fun openDb() {
        db = dbHelper.writableDatabase
    }

    fun closeDb() {
        db.close()
    }

}
