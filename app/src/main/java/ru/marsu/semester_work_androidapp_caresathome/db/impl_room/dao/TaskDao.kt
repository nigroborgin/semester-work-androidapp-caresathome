package ru.marsu.semester_work_androidapp_caresathome.db.impl_room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.entity.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM task as t WHERE t.id = :id")
    fun getOneById(id: Int): Task

    @Query("SELECT * FROM task as t WHERE t.status_id = :statusId")
    fun getByStatus(statusId: Int): List<Task>

    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(task: Task) : Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(task: Task) : Int

    @Delete(Task::class)
    fun delete(task: Task) : Int

    @Query("DELETE FROM task")
    fun deleteAll()

}