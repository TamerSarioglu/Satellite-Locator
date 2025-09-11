package com.tamersarioglu.satellitelocator.presentation.ui.detail

sealed class SatelliteDetailUiEffect {
    data class ShowError(val message: String) : SatelliteDetailUiEffect()
    data class ShowSuccess(val message: String) : SatelliteDetailUiEffect()
    object NavigateBack : SatelliteDetailUiEffect()
}