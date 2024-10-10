package ru.marsu.semester_work_androidapp_caresathome.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Класс, работающий непосредственно с БД
class DbHelper(context: Context) : SQLiteOpenHelper(context, DbScheme.DATABASE_NAME, null, DbScheme.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DbScheme.Periodicity.CREATE_TABLE)
        db?.execSQL(DbScheme.Periodicity.FILL_DEFAULT_VALUE)
        db?.execSQL(DbScheme.Status.CREATE_TABLE)
        db?.execSQL(DbScheme.Status.FILL_DEFAULT_VALUE)
        db?.execSQL(DbScheme.Task.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DbScheme.Task.DELETE_TABLE)
        db?.execSQL(DbScheme.Periodicity.DELETE_TABLE)
        db?.execSQL(DbScheme.Status.DELETE_TABLE)
        onCreate(db)
    }

}
