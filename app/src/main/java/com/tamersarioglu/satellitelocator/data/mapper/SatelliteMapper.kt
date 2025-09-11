package com.tamersarioglu.satellitelocator.data.mapper

import com.tamersarioglu.satellitelocator.data.local.entity.SatelliteEntity
import com.tamersarioglu.satellitelocator.data.model.SatelliteDto
import com.tamersarioglu.satellitelocator.domain.model.Satellite

// DTO -> Domain
fun SatelliteDto.toDomain(): Satellite {
    return Satellite(
        id = id,
        name = name,
        isActive = active
    )
}

// Entity -> Domain
fun SatelliteEntity.toDomain(): Satellite {
    return Satellite(
        id = id,
        name = name,
        isActive = isActive
    )
}

// DTO -> Entity
fun SatelliteDto.toEntity(): SatelliteEntity {
    return SatelliteEntity(
        id = id,
        name = name,
        isActive = active
    )
}

// List Extensions
fun List<SatelliteEntity>.toDomainList(): List<Satellite> = map { it.toDomain() }
fun List<SatelliteDto>.toEntityList(): List<SatelliteEntity> = map { it.toEntity() }