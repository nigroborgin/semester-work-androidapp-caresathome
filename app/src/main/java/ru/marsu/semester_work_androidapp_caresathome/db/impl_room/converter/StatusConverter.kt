package ru.marsu.semester_work_androidapp_caresathome.db.impl_room.converter

import ru.marsu.semester_work_androidapp_caresathome.db.impl_room.entity.Status
import ru.marsu.semester_work_androidapp_caresathome.dto.StatusDto

class StatusConverter private constructor() {

    companion object {
        val instance: StatusConverter = StatusConverter()
    }

    fun convert(entity: Status): StatusDto {
        val statusDto = StatusDto(
            id = entity.id,
            status = entity.status
        )

        return statusDto
    }

    fun convert(dto: StatusDto): Status {
        val status = Status(
            id = dto.id,
            status = dto.status
        )

        return status
    }

}