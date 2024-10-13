package ru.marsu.semester_work_androidapp_caresathome

import android.content.Context
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

    companion object {
        val instance: ServiceLocator = ServiceLocator()
    }

    fun init(context: Context) {

        // Uncomment this for use ROOM (SQLite)
//        val roomLocalDb = RoomLocalDb.getDb(context)!!
//        taskRepository = RoomTaskRepository(
//            taskDao = roomLocalDb.taskDao(),
//            statusDao = roomLocalDb.statusDao(),
//            periodicityDao = roomLocalDb.periodicityDao()
//        )

        // Uncomment this for use REALM (MongoDB)
        val realmLocalDb = RealmLocalDb.getDb(context)!!
        taskRepository = RealmTaskRepository(
            taskDao = TaskDao(realmLocalDb),
            statusDao = StatusDao(realmLocalDb),
            periodicityDao = PeriodicityDao(realmLocalDb)
        )
    }
}
