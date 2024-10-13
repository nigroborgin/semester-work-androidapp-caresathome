package ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.io.Serializable

class Task : RealmObject, Serializable {

    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var idForApp: Int = -1

    var title: String = ""
    var dueDateTime: String = ""
    var timeWitchCompleted: String = ""
    var periodicityId: Int = -1
    var statusId: Int = -1
    var patient: String = ""
    var bloodPressure: String = ""
    var heartRate: Int = -1
    var isBloodSampleCollection: Int = -1

    constructor()

    constructor(id: Int) {
        this.idForApp = id
    }

    constructor(
        id: Int,
        title: String,
        dueDateTime: String,
        timeWitchCompleted: String,
        periodicityId: Int,
        statusId: Int,
        patient: String,
        bloodPressure: String,
        heartRate: Int,
        isBloodSampleCollection: Int
    ) {
        this.idForApp = id
        this.title = title
        this.dueDateTime = dueDateTime
        this.timeWitchCompleted = timeWitchCompleted
        this.periodicityId = periodicityId
        this.statusId = statusId
        this.patient = patient
        this.bloodPressure = bloodPressure
        this.heartRate = heartRate
        this.isBloodSampleCollection = isBloodSampleCollection
    }

}
