package ru.marsu.semester_work_androidapp_caresathome.db.impl_room

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.dao.PeriodicityDao
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.dao.StatusDao
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.dao.TaskDao
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.entity.Periodicity
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.entity.Status
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.entity.Task

@Database(
    entities = [Periodicity::class, Status::class, Task::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = RoomLocalDb.Migration1To2::class)
    ]
)
abstract class RoomLocalDb : RoomDatabase() {

    abstract fun periodicityDao(): PeriodicityDao
    abstract fun statusDao(): StatusDao
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: RoomLocalDb? = null

        private val migration = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE status RENAME COLUMN title TO status")
            }
        }

        fun getDb(context: Context): RoomLocalDb? {
            if (INSTANCE != null) {
                return INSTANCE
            } else {
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        RoomLocalDb::class.java,
                        "MyLab.db"
                    )
                        .createFromAsset("database/MyLab.db")
                        .allowMainThreadQueries()
                        .addMigrations(migration)
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