package ru.marsu.semester_work_androidapp_caresathome.db.impl_room.dao

import androidx.room.Dao
import androidx.room.Query
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.entity.Periodicity

@Dao
interface PeriodicityDao {

    @Query("SELECT * FROM periodicity as p WHERE p.id = :id")
    fun getOneById(id: Int): Periodicity

    @Query("SELECT * FROM periodicity")
    fun getAll(): List<Periodicity>

}