package com.tamersarioglu.satellitelocator.domain.model

data class Position(
    val x: Double,
    val y: Double
)

data class SatellitePosition(
    val satelliteId: Int,
    val positions: List<Position>
)