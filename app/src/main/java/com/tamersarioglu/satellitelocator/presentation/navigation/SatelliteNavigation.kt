package com.tamersarioglu.satellitelocator.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class SatelliteDestination {

    @Serializable
    data object SatelliteList : SatelliteDestination()

    @Serializable
    data class SatelliteDetail(val satelliteId: Int) : SatelliteDestination()
}