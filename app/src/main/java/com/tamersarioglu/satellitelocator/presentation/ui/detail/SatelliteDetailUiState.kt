package com.tamersarioglu.satellitelocator.presentation.ui.detail

import com.tamersarioglu.satellitelocator.domain.model.Position
import com.tamersarioglu.satellitelocator.domain.model.Satellite
import com.tamersarioglu.satellitelocator.domain.model.SatelliteDetail

data class SatelliteDetailUiState(
    val satellite: Satellite? = null,
    val satelliteDetail: SatelliteDetail? = null,
    val currentPosition: Position? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    val isDataLoaded: Boolean
        get() = satellite != null && satelliteDetail != null
}