package com.tamersarioglu.satellitelocator.presentation.ui.list

import com.tamersarioglu.satellitelocator.domain.model.Satellite

data class SatelliteListUiState(
    val satellites: List<Satellite> = emptyList(),
    val filteredSatellites: List<Satellite> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
) {
    val displaySatellites: List<Satellite>
        get() = if (searchQuery.isBlank()) satellites else filteredSatellites
}