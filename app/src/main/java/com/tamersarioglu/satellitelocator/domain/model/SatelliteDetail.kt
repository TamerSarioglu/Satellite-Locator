package com.tamersarioglu.satellitelocator.domain.model

import java.time.LocalDate

data class SatelliteDetail(
    val id: Int,
    val costPerLaunch: Long,
    val firstFlight: LocalDate,
    val height: Int,
    val mass: Long
)