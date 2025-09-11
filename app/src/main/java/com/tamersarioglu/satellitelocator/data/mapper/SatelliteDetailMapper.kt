package com.tamersarioglu.satellitelocator.data.mapper

import com.tamersarioglu.satellitelocator.data.local.entity.SatelliteDetailEntity
import com.tamersarioglu.satellitelocator.data.model.SatelliteDetailDto
import com.tamersarioglu.satellitelocator.domain.model.SatelliteDetail
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

// DTO -> Domain
fun SatelliteDetailDto.toDomain(): SatelliteDetail {
    return SatelliteDetail(
        id = id,
        costPerLaunch = costPerLaunch,
        firstFlight = LocalDate.parse(firstFlight, dateFormatter),
        height = height,
        mass = mass
    )
}

// Entity -> Domain
fun SatelliteDetailEntity.toDomain(): SatelliteDetail {
    return SatelliteDetail(
        id = id,
        costPerLaunch = costPerLaunch,
        firstFlight = LocalDate.parse(firstFlight, dateFormatter),
        height = height,
        mass = mass
    )
}

// DTO -> Entity
fun SatelliteDetailDto.toEntity(): SatelliteDetailEntity {
    return SatelliteDetailEntity(
        id = id,
        costPerLaunch = costPerLaunch,
        firstFlight = firstFlight,
        height = height,
        mass = mass
    )
}

// List Extension
fun List<SatelliteDetailDto>.toEntityList(): List<SatelliteDetailEntity> = map { it.toEntity() }