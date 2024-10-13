package ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.dao

import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.entity.Periodicity

class PeriodicityDao(
    val db: Realm
) {

    fun getOneById(idForApp: Int): Periodicity {
        val periodicity: Periodicity = db.query<Periodicity>("idForApp == $idForApp")
            .first()
            .find()!!
        return periodicity
    }

    fun getAll(): List<Periodicity> {
        val periodicityList: List<Periodicity> = db.query<Periodicity>()
            .find()
            .stream()
            .filter { it != null }
            .toList()
        return periodicityList
    }
}