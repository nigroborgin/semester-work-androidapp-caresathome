package ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.converter

import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.entity.Status
import ru.marsu.semester_work_androidapp_caresathome.dto.StatusDto

class StatusConverter private constructor() {

    companion object {
        val instance: StatusConverter = StatusConverter()
    }

    fun convert(entity: Status): StatusDto {
        val statusDto = StatusDto(
            id = entity.idForApp,
            status = entity.status
        )

        return statusDto
    }

    fun convert(dto: StatusDto): Status {
        val status = Status(
            id = dto.id!!,
            status = dto.status!!
        )

        return status
    }

}