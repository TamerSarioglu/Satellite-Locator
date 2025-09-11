package com.tamersarioglu.satellitelocator.presentation.ui.list

import com.tamersarioglu.satellitelocator.domain.model.Satellite

sealed class SatelliteListUiState {
    object Loading : SatelliteListUiState()

    data class Success(
        val satellites: List<Satellite>,
        val displaySatellites: List<Satellite>,
        val searchQuery: String = ""
    ) : SatelliteListUiState()

    data class Error(val message: String) : SatelliteListUiState()
}