package ru.marsu.semester_work_androidapp_caresathome

import android.content.Context
import ru.marsu.semester_work_androidapp_caresathome.db.LocalDb
import ru.marsu.semester_work_androidapp_caresathome.db.repository.TaskRepository

class ServiceLocation private constructor() {

    var services = HashMap<String, Any>()

    companion object {
        val instance: ServiceLocation = ServiceLocation()
    }

    fun init(context : Context) {
        val localDb = LocalDb.getDb(context)!!
        val taskRepository = TaskRepository(localDb.taskDao(), localDb.statusDao(), localDb.periodicityDao())
        services["taskRepository"] = taskRepository
    }
}
