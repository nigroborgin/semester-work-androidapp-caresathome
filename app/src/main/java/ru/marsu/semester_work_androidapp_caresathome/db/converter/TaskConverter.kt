package ru.marsu.semester_work_androidapp_caresathome.db.converter

import ru.marsu.semester_work_androidapp_caresathome.db.entity.Task
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
            id = entity.id,
            title = entity.title,
            dueDateTime = LocalDateTime.parse(entity.dueDateTime),
            timeWitchCompleted = if (!entity.timeWitchCompleted.equals("null"))
                LocalDateTime.parse(entity.timeWitchCompleted) else null,
            periodicity = PeriodicityDto(entity.periodicityId!!),
            status = StatusDto(entity.statusId!!),
            patient = entity.patient,
            bloodPressure = entity.bloodPressure,
            heartRate = entity.heartRate,
            isBloodSampleCollection = entity.isBloodSampleCollection == 1
        )

        return taskDto
    }

    fun convert(dto: TaskDto): Task {
        val task = Task(
            id = dto.id,
            title = dto.title,
            dueDateTime = dto.dueDateTime.toString(),
            timeWitchCompleted = dto.timeWitchCompleted.toString(),
            periodicityId = dto.periodicity?.id,
            statusId = dto.status?.id,
            patient = dto.patient,
            bloodPressure = dto.bloodPressure,
            heartRate = dto.heartRate,
            isBloodSampleCollection = if (dto.isBloodSampleCollection == true) 1 else 0
        )

        return task
    }

}