package ru.marsu.semester_work_androidapp_caresathome.db.impl_room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity("task")
data class Task (

    @PrimaryKey(true)
    @ColumnInfo("id")
    val id : Int? = null,
    @ColumnInfo("title")
    var title: String?,
    @ColumnInfo("due_date_time")
    var dueDateTime: String?,
    @ColumnInfo("time_witch_completed")
    var timeWitchCompleted: String?,
    @ColumnInfo("periodicity_id")
    var periodicityId: Int?,
    @ColumnInfo("status_id")
    var statusId: Int?,
    @ColumnInfo("patient")
    var patient: String?,
    @ColumnInfo("blood_pressure")
    var bloodPressure: String?,
    @ColumnInfo("heart_rate")
    var heartRate: Int?,
    @ColumnInfo("blood_sample_collection")
    var isBloodSampleCollection: Int?

) : Serializable {
    constructor(id: Int) : this(id, null, null, null, null, null, null, null, null, null)
    constructor() : this(null, null, null, null, null, null, null, null, null, null)
}
