package ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId
import java.io.Serializable

// Periodicity: 1-"Everyday", 2-"Alternate Day", 3-"Third Day", 4-"Weekly", 5-"Monthly"
class Periodicity : RealmObject, Serializable {

    @PrimaryKey
    var _id: ObjectId = BsonObjectId()
    var idForApp: Int = -1
    var period: String = ""

    constructor()

    constructor(id: Int) {
        this.idForApp = id
    }

    constructor(id: Int, period: String) {
        this.idForApp = id
        this.period = period
    }

}
