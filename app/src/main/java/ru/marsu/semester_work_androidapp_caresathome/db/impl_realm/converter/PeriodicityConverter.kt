package ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.converter

import ru.marsu.semester_work_androidapp_caresathome.db.impl_realm.entity.Periodicity
import ru.marsu.semester_work_androidapp_caresathome.dto.PeriodicityDto

class PeriodicityConverter private constructor() {

    companion object {
        val instance: PeriodicityConverter = PeriodicityConverter()
    }

    fun convert(entity: Periodicity): PeriodicityDto {
        val periodicityDto = PeriodicityDto(
            id = entity.idForApp,
            period = entity.period
        )

        return periodicityDto
    }

    fun convert(dto: PeriodicityDto): Periodicity {
        val periodicity = Periodicity(
            id = dto.id!!,
            period = dto.period!!
        )

        return periodicity
    }

}