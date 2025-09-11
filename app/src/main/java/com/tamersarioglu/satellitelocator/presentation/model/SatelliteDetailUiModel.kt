package com.tamersarioglu.satellitelocator.presentation.model

import java.time.LocalDate

data class SatelliteDetailUiModel(
    val id: Int,
    val name: String,
    val costPerLaunch: Long,
    val firstFlight: LocalDate,
    val height: Int,
    val mass: Long,
    val currentPosition: PositionUiModel?
)

data class PositionUiModel(
    val x: Double,
    val y: Double
)