package ru.marsu.semester_work_androidapp_caresathome.entity

import java.io.Serializable
import java.time.LocalDateTime

data class Task (
    var id: Int?,
    var title: String?,
    var dueDateTime: LocalDateTime?,
    var timeWitchCompleted: LocalDateTime?,
    var periodicity: Periodicity?,
    var status: Status?,
    var patient: String?,
    var bloodPressure: String?,
    var heartRate: Int?,
    var isBloodSampleCollection: Boolean?
) : Serializable {
    constructor() : this(null, null, null, null, null, null, null, null, null, null)
}
