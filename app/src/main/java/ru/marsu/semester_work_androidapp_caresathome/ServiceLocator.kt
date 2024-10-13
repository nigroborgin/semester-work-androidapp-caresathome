package ru.marsu.semester_work_androidapp_caresathome

import android.content.Context
import io.realm.kotlin.Realm
import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.RealmLocalDb
import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.dao.PeriodicityDao
import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.dao.StatusDao
import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.dao.TaskDao
import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.repository.RealmTaskRepository
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.RoomLocalDb
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.repository.RoomTaskRepository
import ru.marsu.semester_work_androidapp_caresathome.db.repository.TaskRepository

class ServiceLocator private constructor() {

    lateinit var taskRepository: TaskRepository
    lateinit var roomLocalDb: RoomLocalDb
    lateinit var realmLocalDb: Realm

    companion object {
        val instance: ServiceLocator = ServiceLocator()
    }

    fun init(context: Context) {
        // Uncomment this for use ROOM (SQLite)
//        roomLocalDb = RoomLocalDb.getDb(context)!!
//        taskRepository = RoomTaskRepository(
//            taskDao = roomLocalDb.taskDao(),
//            statusDao = roomLocalDb.statusDao(),
//            periodicityDao = roomLocalDb.periodicityDao()
//        )

        // Uncomment this for use REALM (MongoDB)
        realmLocalDb = RealmLocalDb.getDb(context)!!
        taskRepository = RealmTaskRepository(
            taskDao = TaskDao(realmLocalDb),
            statusDao = StatusDao(realmLocalDb),
            periodicityDao = PeriodicityDao(realmLocalDb)
        )
    }

    fun init(context: Context, dbVariant: DbVariant) {
        if (dbVariant == DbVariant.ROOM) {
            initRoomDb(context)
        }
        if (dbVariant == DbVariant.REALM) {
            initRealmDb(context)
        }
    }

    private fun initRealmDb(context: Context) {
        realmLocalDb = RealmLocalDb.getDb(context)!!
        taskRepository = RealmTaskRepository(
            taskDao = TaskDao(realmLocalDb),
            statusDao = StatusDao(realmLocalDb),
            periodicityDao = PeriodicityDao(realmLocalDb)
        )
    }

    private fun initRoomDb(context: Context) {
        // Uncomment this for use ROOM (SQLite)
        roomLocalDb = RoomLocalDb.getDb(context)!!
        taskRepository = RoomTaskRepository(
            taskDao = roomLocalDb.taskDao(),
            statusDao = roomLocalDb.statusDao(),
            periodicityDao = roomLocalDb.periodicityDao()
        )
    }

    enum class DbVariant {
        ROOM, REALM
    }

}
