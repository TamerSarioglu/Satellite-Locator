package com.tamersarioglu.satellitelocator.presentation.model

import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

data class SatelliteDetailUiModel(
    val id: Int,
    val name: String,
    val costPerLaunch: Long,
    val firstFlight: LocalDate,
    val height: Int,
    val mass: Long,
    val currentPosition: PositionUiModel?
) {
    val formattedCost: String
        get() = NumberFormat.getCurrencyInstance(Locale.US).format(costPerLaunch)

    val formattedFirstFlight: String
        get() = firstFlight.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

    val formattedHeight: String
        get() = "${height}m"

    val formattedMass: String
        get() = NumberFormat.getNumberInstance().format(mass) + "kg"
}

data class PositionUiModel(
    val x: Double,
    val y: Double
) {
    val formattedPosition: String
        get() = "(${String.format("%.3f", x)}, ${String.format("%.3f", y)})"
}