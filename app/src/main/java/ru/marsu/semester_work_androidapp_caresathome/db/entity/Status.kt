package ru.marsu.semester_work_androidapp_caresathome.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// Status: 1-"completed", 2-"pending", 3-"missed"
@Entity("status")
data class Status (

    @PrimaryKey(true)
    @ColumnInfo("id")
    val id : Int? = null,
    @ColumnInfo("title")
    var status : String?

) : Serializable {
    constructor(id: Int) : this(id, null)
    constructor() : this(null, null)
}
