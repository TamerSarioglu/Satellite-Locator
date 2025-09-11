package com.tamersarioglu.satellitelocator.presentation.ui.detail

import com.tamersarioglu.satellitelocator.domain.model.Position
import com.tamersarioglu.satellitelocator.domain.model.Satellite
import com.tamersarioglu.satellitelocator.domain.model.SatelliteDetail

sealed class SatelliteDetailUiState {
    object Loading : SatelliteDetailUiState()

    data class Success(
        val satellite: Satellite,
        val satelliteDetail: SatelliteDetail,
        val currentPosition: Position? = null
    ) : SatelliteDetailUiState()

    data class Error(val message: String) : SatelliteDetailUiState()
}