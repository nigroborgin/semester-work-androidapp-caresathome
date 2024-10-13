package ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.dao

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.entity.Status

class StatusDao(
    val db: Realm
) {

    fun getOneById(idForApp: Int): Status {
        val status: Status = db.query<Status>("idForApp == $idForApp")
            .first()
            .find()!!
        return status
    }

    fun getAll(): List<Status> {
        val statusList: List<Status> = db.query<Status>()
            .find()
            .stream()
            .filter { it != null }
            .toList()
        return statusList
    }
}