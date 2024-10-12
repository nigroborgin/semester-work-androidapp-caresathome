package ru.marsu.semester_work_androidapp_caresathome.db.dao

import androidx.room.Dao
import androidx.room.Query
import ru.marsu.semester_work_androidapp_caresathome.db.entity.Status

@Dao
interface StatusDao {

    @Query("SELECT * FROM status as s WHERE s.id = :id")
    fun getOneById(id: Int): Status

    @Query("SELECT * FROM status")
    fun getAll(): List<Status>

}