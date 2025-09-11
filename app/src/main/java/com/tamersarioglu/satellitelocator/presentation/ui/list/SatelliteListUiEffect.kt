package com.tamersarioglu.satellitelocator.presentation.ui.list

sealed class SatelliteListUiEffect {
    data class ShowError(val message: String) : SatelliteListUiEffect()
    data class ShowSuccess(val message: String) : SatelliteListUiEffect()
    data class NavigateToSatelliteDetail(val satelliteId: Int) : SatelliteListUiEffect()
}