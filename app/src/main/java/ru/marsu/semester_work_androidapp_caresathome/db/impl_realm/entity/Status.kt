package ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.BsonObjectId
import org.mongodb.kbson.ObjectId
import java.io.Serializable

// Status: 1-"completed", 2-"pending", 3-"missed"
class Status : RealmObject, Serializable {

    @PrimaryKey
    var _id: ObjectId = BsonObjectId()
    var idForApp: Int = -1
    var status: String = ""

    constructor()

    constructor(id: Int) {
        this.idForApp = id
    }

    constructor(id: Int, status: String) {
        this.idForApp = id
        this.status = status
    }
}
