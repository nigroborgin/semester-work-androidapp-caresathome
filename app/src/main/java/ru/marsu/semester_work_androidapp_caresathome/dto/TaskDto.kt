package ru.marsu.semester_work_androidapp_caresathome.dto

import java.io.Serializable
import java.time.LocalDateTime

data class TaskDto (

    var id : Int? = null,
    var title: String?,
    var dueDateTime: LocalDateTime?,
    var timeWitchCompleted: LocalDateTime?,
    var periodicity: PeriodicityDto?,
    var status: StatusDto?,
    var patient: String?,
    var bloodPressure: String?,
    var heartRate: Int?,
    var isBloodSampleCollection: Boolean?

) : Serializable {
    constructor(id: Int) : this(id, null, null, null, null, null, null, null, null, null)
    constructor() : this(null, null, null, null, null, null, null, null, null, null)
}
