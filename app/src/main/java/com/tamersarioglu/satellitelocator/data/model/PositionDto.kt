package com.tamersarioglu.satellitelocator.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PositionDto(
    val posX: Double,
    val posY: Double
)

@Serializable
data class SatellitePositionDto(
    val id: String,
    val positions: List<PositionDto>
)

@Serializable
data class PositionsResponse(
    val list: List<SatellitePositionDto>
)