package com.tamersarioglu.satellitelocator.data.mapper

import com.tamersarioglu.satellitelocator.data.model.PositionDto
import com.tamersarioglu.satellitelocator.data.model.SatellitePositionDto
import com.tamersarioglu.satellitelocator.domain.model.Position
import com.tamersarioglu.satellitelocator.domain.model.SatellitePosition
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PositionMapper @Inject constructor() {

    // DTO → Domain
    fun toDomain(dto: PositionDto): Position {
        return Position(
            x = dto.posX,
            y = dto.posY
        )
    }

    fun toDomain(dto: SatellitePositionDto): SatellitePosition {
        return SatellitePosition(
            satelliteId = dto.id.toInt(),
            positions = dto.positions.map { toDomain(it) }
        )
    }

    // List transformations
    fun toDomainList(dtos: List<PositionDto>): List<Position> {
        return dtos.map { toDomain(it) }
    }

    fun toSatellitePositionList(dtos: List<SatellitePositionDto>): List<SatellitePosition> {
        return dtos.map { toDomain(it) }
    }
}