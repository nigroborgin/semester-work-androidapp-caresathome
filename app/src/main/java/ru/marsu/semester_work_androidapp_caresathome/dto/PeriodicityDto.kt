package ru.marsu.semester_work_androidapp_caresathome.dto

import java.io.Serializable

// Periodicity: 1-"Everyday", 2-"Alternate Day", 3-"Third Day", 4-"Weekly", 5-"Monthly"
data class PeriodicityDto (

    val id : Int? = null,
    var period : String?

) : Serializable {
    constructor(id: Int) : this(id, null)
    constructor() : this(null, null)
}
