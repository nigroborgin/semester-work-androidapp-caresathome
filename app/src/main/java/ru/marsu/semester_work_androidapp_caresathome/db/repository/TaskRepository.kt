package ru.marsu.semester_work_androidapp_caresathome.db.repository

import ru.marsu.semester_work_androidapp_caresathome.dto.StatusDto
import ru.marsu.semester_work_androidapp_caresathome.dto.TaskDto

interface TaskRepository {

    /**
     * @return `id` of created `Task`
     */
    fun create(taskDto: TaskDto): Int

    fun getOneById(id: Int): TaskDto

    fun getAll(): List<TaskDto>

    fun getByStatus(statusDto: StatusDto): List<TaskDto>

    /**
     * @return `count` of updated `Task`
     */
    fun update(taskDto: TaskDto): Int

    /**
     * @return `count` of deleted `Task`
     */
    fun delete(taskDto: TaskDto): Int

}