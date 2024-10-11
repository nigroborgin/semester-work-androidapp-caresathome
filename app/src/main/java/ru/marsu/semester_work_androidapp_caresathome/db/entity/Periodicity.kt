package ru.marsu.semester_work_androidapp_caresathome.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// Periodicity: 1-"Everyday", 2-"Alternate Day", 3-"Third Day", 4-"Weekly", 5-"Monthly"
@Entity("periodicity")
data class Periodicity (

    @PrimaryKey(true)
    @ColumnInfo("id")
    val id : Int? = null,
    @ColumnInfo("title")
    var period : String?

) : Serializable {
    constructor(id: Int) : this(id, null)
    constructor() : this(null, null)
}
