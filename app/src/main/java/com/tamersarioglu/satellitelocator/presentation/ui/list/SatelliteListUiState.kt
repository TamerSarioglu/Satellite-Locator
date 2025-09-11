package com.tamersarioglu.satellitelocator.presentation.ui.list

import com.tamersarioglu.satellitelocator.domain.model.Satellite

sealed class SatelliteListUiState {
    object Loading : SatelliteListUiState()

    data class Success(
        val satellites: List<Satellite>,
        val searchQuery: String = ""
    ) : SatelliteListUiState() {
        val filteredSatellites: List<Satellite>
            get() = if (searchQuery.isBlank()) {
                satellites
            } else {
                val trimmedQuery = searchQuery.trim().lowercase()
                satellites.filter { satellite ->
                    satellite.name.lowercase().contains(trimmedQuery)
                }
            }

        val displaySatellites: List<Satellite>
            get() = filteredSatellites
    }

    data class Error(val message: String) : SatelliteListUiState()
}