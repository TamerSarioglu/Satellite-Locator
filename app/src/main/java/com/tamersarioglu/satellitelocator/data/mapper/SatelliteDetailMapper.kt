package com.tamersarioglu.satellitelocator.data.mapper

import com.tamersarioglu.satellitelocator.data.local.entity.SatelliteDetailEntity
import com.tamersarioglu.satellitelocator.data.model.SatelliteDetailDto
import com.tamersarioglu.satellitelocator.domain.model.SatelliteDetail
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SatelliteDetailMapper @Inject constructor() {

    private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun toDomain(dto: SatelliteDetailDto): SatelliteDetail {
        return SatelliteDetail(
            id = dto.id,
            costPerLaunch = dto.costPerLaunch,
            firstFlight = LocalDate.parse(dto.firstFlight, dateFormatter),
            height = dto.height,
            mass = dto.mass
        )
    }

    fun toDomain(entity: SatelliteDetailEntity): SatelliteDetail {
        return SatelliteDetail(
            id = entity.id,
            costPerLaunch = entity.costPerLaunch,
            firstFlight = LocalDate.parse(entity.firstFlight, dateFormatter),
            height = entity.height,
            mass = entity.mass
        )
    }

    fun toEntity(dto: SatelliteDetailDto): SatelliteDetailEntity {
        return SatelliteDetailEntity(
            id = dto.id,
            costPerLaunch = dto.costPerLaunch,
            firstFlight = dto.firstFlight,
            height = dto.height,
            mass = dto.mass
        )
    }

    fun dtosToDomainList(dtos: List<SatelliteDetailDto>): List<SatelliteDetail> {
        return dtos.map { toDomain(it) }
    }

    fun entitiesToDomainList(entities: List<SatelliteDetailEntity>): List<SatelliteDetail> {
        return entities.map { toDomain(it) }
    }

    fun dtosToEntityList(dtos: List<SatelliteDetailDto>): List<SatelliteDetailEntity> {
        return dtos.map { toEntity(it) }
    }
}