package com.tamersarioglu.satellitelocator.presentation.model

data class SatelliteUiModel(
    val id: Int,
    val name: String,
    val isActive: Boolean
) {
    val statusText: String
        get() = if (isActive) "Active" else "Passive"

    val statusColor: SatelliteStatus
        get() = if (isActive) SatelliteStatus.ACTIVE else SatelliteStatus.INACTIVE
}

enum class SatelliteStatus {
    ACTIVE,
    INACTIVE
}