package com.tamersarioglu.satellitelocator.presentation.ui.list

import com.tamersarioglu.satellitelocator.domain.model.Satellite

val SatelliteListUiState.satellites: List<Satellite>
    get() = (this as? SatelliteListUiState.Success)?.satellites ?: emptyList()

val SatelliteListUiState.searchQuery: String
    get() = (this as? SatelliteListUiState.Success)?.searchQuery ?: ""
