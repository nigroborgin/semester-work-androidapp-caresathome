package ru.marsu.semester_work_androidapp_caresathome

import android.content.Context
import ru.marsu.semester_work_androidapp_caresathome.db.DbManager
import ru.marsu.semester_work_androidapp_caresathome.db.dao.PeriodicityDao
import ru.marsu.semester_work_androidapp_caresathome.db.dao.StatusDao
import ru.marsu.semester_work_androidapp_caresathome.db.dao.TaskDao
import ru.marsu.semester_work_androidapp_caresathome.db.repository.TaskRepository

class ServiceLocation private constructor() {

    var services = HashMap<String, Any>()

    companion object {
        val instance: ServiceLocation = ServiceLocation()
    }

    fun init(context : Context) {

        val dbManager = DbManager(context)
        dbManager.openDb()

        val periodicityDao = PeriodicityDao(dbManager.db)
        val statusDao = StatusDao(dbManager.db)
        val taskDao = TaskDao(dbManager.db)

        val taskRepository = TaskRepository(taskDao, statusDao, periodicityDao)

        services["dbManager"] = dbManager
        services["periodicityDao"] = periodicityDao
        services["statusDao"] = statusDao
        services["taskDao"] = taskDao
        services["taskRepository"] = taskRepository
    }
}
