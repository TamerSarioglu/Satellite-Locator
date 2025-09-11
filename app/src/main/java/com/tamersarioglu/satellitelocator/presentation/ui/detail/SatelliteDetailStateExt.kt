package com.tamersarioglu.satellitelocator.presentation.ui.detail

import com.tamersarioglu.satellitelocator.domain.model.Satellite

val SatelliteDetailUiState.satellite: Satellite?
    get() = (this as? SatelliteDetailUiState.Success)?.satellite

val SatelliteDetailUiState.errorMessage: String?
    get() = (this as? SatelliteDetailUiState.Error)?.message