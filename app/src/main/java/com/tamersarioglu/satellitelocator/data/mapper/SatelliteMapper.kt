package com.tamersarioglu.satellitelocator.data.mapper

import com.tamersarioglu.satellitelocator.data.local.entity.SatelliteEntity
import com.tamersarioglu.satellitelocator.data.model.SatelliteDto
import com.tamersarioglu.satellitelocator.domain.model.Satellite
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SatelliteMapper @Inject constructor() {

    fun toDomain(dto: SatelliteDto): Satellite {
        return Satellite(
            id = dto.id,
            name = dto.name,
            isActive = dto.active
        )
    }

    fun toDomain(entity: SatelliteEntity): Satellite {
        return Satellite(
            id = entity.id,
            name = entity.name,
            isActive = entity.isActive
        )
    }

    fun toEntity(dto: SatelliteDto): SatelliteEntity {
        return SatelliteEntity(
            id = dto.id,
            name = dto.name,
            isActive = dto.active
        )
    }

    fun dtosToDomainList(dtos: List<SatelliteDto>): List<Satellite> {
        return dtos.map { toDomain(it) }
    }

    fun entitiesToDomainList(entities: List<SatelliteEntity>): List<Satellite> {
        return entities.map { toDomain(it) }
    }

    fun dtosToEntityList(dtos: List<SatelliteDto>): List<SatelliteEntity> {
        return dtos.map { toEntity(it) }
    }
}