package ru.marsu.semester_work_androidapp_caresathome.entity

import java.io.Serializable

// Status: 1-"completed", 2-"pending", 3-"missed"
data class Status (
    val id : Int,
    var status : String?
) : Serializable {
    constructor(id: Int) : this(id, null)
}
