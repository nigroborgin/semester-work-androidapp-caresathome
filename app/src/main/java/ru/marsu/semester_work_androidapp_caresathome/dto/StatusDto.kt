package ru.marsu.semester_work_androidapp_caresathome.dto

import java.io.Serializable

// Status: 1-"completed", 2-"pending", 3-"missed"
data class StatusDto (

    val id : Int? = null,
    var status : String?

) : Serializable {
    constructor(id: Int) : this(id, null)
    constructor() : this(null, null)
}
