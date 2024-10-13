package ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.converter

import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.entity.Task
import ru.marsu.semester_work_androidapp_caresathome.dto.PeriodicityDto
import ru.marsu.semester_work_androidapp_caresathome.dto.StatusDto
import ru.marsu.semester_work_androidapp_caresathome.dto.TaskDto
import java.time.LocalDateTime

class TaskConverter private constructor() {

    companion object {
        val instance: TaskConverter = TaskConverter()
    }

    fun convert(entity: Task): TaskDto {
        val taskDto = TaskDto(
            id = entity.idForApp,
            title = entity.title,
            dueDateTime = LocalDateTime.parse(entity.dueDateTime),
            timeWitchCompleted = if (entity.timeWitchCompleted != "null")
                LocalDateTime.parse(entity.timeWitchCompleted) else null,
            periodicity = PeriodicityDto(entity.periodicityId),
            status = StatusDto(entity.statusId),
            patient = entity.patient,
            bloodPressure = entity.bloodPressure,
            heartRate = if (entity.heartRate > 0) entity.heartRate else null,
            isBloodSampleCollection = entity.isBloodSampleCollection == 1
        )

        return taskDto
    }

    fun convert(dto: TaskDto): Task {
        val task = Task().apply {
            if (dto.id != null) idForApp = dto.id!!
            title = dto.title!!
            dueDateTime = dto.dueDateTime.toString()
            timeWitchCompleted = dto.timeWitchCompleted.toString()
            periodicityId = dto.periodicity?.id!!
            statusId = dto.status?.id!!
            patient = dto.patient!!
            if (dto.bloodPressure != null) bloodPressure = dto.bloodPressure!!
            if (dto.heartRate != null) heartRate = dto.heartRate!!
            isBloodSampleCollection = if (dto.isBloodSampleCollection == true) 1 else 0
        }
        return task
    }

}