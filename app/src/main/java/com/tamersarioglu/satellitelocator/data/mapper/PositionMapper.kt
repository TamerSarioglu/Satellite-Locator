package com.tamersarioglu.satellitelocator.data.mapper

import com.tamersarioglu.satellitelocator.data.model.PositionDto
import com.tamersarioglu.satellitelocator.data.model.SatellitePositionDto
import com.tamersarioglu.satellitelocator.domain.model.Position
import com.tamersarioglu.satellitelocator.domain.model.SatellitePosition

// DTO -> Domain
fun PositionDto.toDomain(): Position {
    return Position(
        x = posX,
        y = posY
    )
}

// SatellitePositionDto -> SatellitePosition
fun SatellitePositionDto.toDomain(): SatellitePosition {
    return SatellitePosition(
        satelliteId = id.toInt(),
        positions = positions.map { it.toDomain() }
    )
}

// List Extensions
fun List<SatellitePositionDto>.toSatellitePositionList(): List<SatellitePosition> =
    map { it.toDomain() }