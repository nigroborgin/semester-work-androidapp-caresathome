package ru.marsu.semester_work_androidapp_caresathome.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import ru.marsu.semester_work_androidapp_caresathome.db.dao.PeriodicityDao
import ru.marsu.semester_work_androidapp_caresathome.db.dao.StatusDao
import ru.marsu.semester_work_androidapp_caresathome.db.dao.TaskDao
import ru.marsu.semester_work_androidapp_caresathome.db.entity.Periodicity
import ru.marsu.semester_work_androidapp_caresathome.db.entity.Status
import ru.marsu.semester_work_androidapp_caresathome.db.entity.Task

@Database(
    entities = [Periodicity::class, Status::class, Task::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = LocalDb.Migration1To2::class)
    ]
)
abstract class LocalDb : RoomDatabase() {

    abstract fun periodicityDao(): PeriodicityDao
    abstract fun statusDao(): StatusDao
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDb? = null

        fun getDb(context: Context): LocalDb? {
            if (INSTANCE != null) {
                return INSTANCE
            } else {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        LocalDb::class.java,
                        "MyLab.db"
                    )
                        .createFromAsset("database/MyLab.db")
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                    return INSTANCE
                }
            }
        }
    }

    @RenameColumn(tableName = "periodicity", fromColumnName = "title", toColumnName = "period")
    class Migration1To2 : AutoMigrationSpec

}