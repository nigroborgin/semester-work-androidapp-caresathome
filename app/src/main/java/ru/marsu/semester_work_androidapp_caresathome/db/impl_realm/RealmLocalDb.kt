package ru.marsu.semester_work_androidapp_caresathome.db.impl_realm

import android.content.Context
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.UpdatePolicy
import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.entity.Periodicity
import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.entity.Status
import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.entity.Task

abstract class RealmLocalDb {

    companion object {
        @Volatile
        private var INSTANCE: Realm? = null

        fun getDb(context: Context): Realm? {
            if (INSTANCE != null) {
                return INSTANCE
            } else {
                synchronized(this) {
                    INSTANCE = Realm.open(
                        RealmConfiguration.create(
                            schema = setOf(
                                Periodicity::class,
                                Status::class,
                                Task::class
                            )
                        )
                    )
                    initStartValue()
                    return INSTANCE
                }
            }
        }

        private fun initStartValue() {
            INSTANCE?.writeBlocking {
                copyToRealm(Status(1, "completed"), UpdatePolicy.ALL)
                copyToRealm(Status(2, "pending"), UpdatePolicy.ALL)
                copyToRealm(Status(3, "missed"), UpdatePolicy.ALL)

                copyToRealm(Periodicity(1, "Everyday"), UpdatePolicy.ALL)
                copyToRealm(Periodicity(2, "Alternate Day"), UpdatePolicy.ALL)
                copyToRealm(Periodicity(3, "Third Day"), UpdatePolicy.ALL)
                copyToRealm(Periodicity(4, "Weekly"), UpdatePolicy.ALL)
                copyToRealm(Periodicity(5, "Monthly"), UpdatePolicy.ALL)
            }
        }
    }
}
