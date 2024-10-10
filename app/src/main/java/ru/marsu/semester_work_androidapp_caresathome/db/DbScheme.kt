package ru.marsu.semester_work_androidapp_caresathome.db

import android.provider.BaseColumns

// Схема БД
object DbScheme {

    const val DATABASE_VERSION = 7
    const val DATABASE_NAME = "MyLessonDB.db"


    object Periodicity : BaseColumns {
        const val TABLE_NAME = "periodicity"

        const val COLUMN_NAME_TITLE = "title"

        const val CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_NAME_TITLE TEXT UNIQUE" +
            ")"

        const val DELETE_TABLE =
            "DROP TABLE IF EXISTS $TABLE_NAME"

        const val FILL_DEFAULT_VALUE =
            "INSERT INTO $TABLE_NAME ($COLUMN_NAME_TITLE) " +
            "VALUES (\'Everyday\'), (\'Alternate Day\'), (\'Third Day\'), (\'Weekly\'), (\'Monthly\')"

    }

    object Status : BaseColumns {
        const val TABLE_NAME = "status"

        const val COLUMN_NAME_TITLE = "title"

        const val CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_NAME_TITLE TEXT UNIQUE" +
            ")"

        const val DELETE_TABLE =
            "DROP TABLE IF EXISTS $TABLE_NAME"

        const val FILL_DEFAULT_VALUE =
            "INSERT INTO $TABLE_NAME ($COLUMN_NAME_TITLE) " +
            "VALUES (\'completed\'), (\'pending\'), (\'missed\')"

    }

    object Task : BaseColumns {
        const val TABLE_NAME = "task"

        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_DUE_DATE_TIME = "due_date_time"
        const val COLUMN_NAME_TIME_WITCH_COMPLETED = "time_witch_completed"
        const val COLUMN_NAME_PERIODICITY_ID = "periodicity_id"
        const val COLUMN_NAME_STATUS_ID = "status_id"

        const val COLUMN_NAME_PATIENT = "patient"

        const val COLUMN_NAME_BLOOD_PRESSURE = "blood_pressure"
        const val COLUMN_NAME_HEART_RATE = "heart_rate"
        const val COLUMN_NAME_BLOOD_SAMPLE_COLLECTION = "blood_sample_collection"


        const val CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_NAME_TITLE TEXT, " +
                    "$COLUMN_NAME_DUE_DATE_TIME TEXT, " +
                    "$COLUMN_NAME_TIME_WITCH_COMPLETED TEXT, " +
                    "$COLUMN_NAME_PERIODICITY_ID INTEGER REFERENCES ${Periodicity.TABLE_NAME} (${BaseColumns._ID}), " +
                    "$COLUMN_NAME_STATUS_ID INTEGER REFERENCES ${Status.TABLE_NAME} (${BaseColumns._ID}), " +
                    "$COLUMN_NAME_PATIENT TEXT, " +
                    "$COLUMN_NAME_BLOOD_PRESSURE TEXT, " +
                    "$COLUMN_NAME_HEART_RATE INTEGER, " +
                    "$COLUMN_NAME_BLOOD_SAMPLE_COLLECTION INTEGER" +
                    ")"

        const val DELETE_TABLE =
            "DROP TABLE IF EXISTS $TABLE_NAME"
    }

}
