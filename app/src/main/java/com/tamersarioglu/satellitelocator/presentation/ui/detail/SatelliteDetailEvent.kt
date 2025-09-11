package com.tamersarioglu.satellitelocator.presentation.ui.detail

sealed class SatelliteDetailEvent {
    object RefreshPosition : SatelliteDetailEvent()
    object RetryLoading : SatelliteDetailEvent()
    object NavigateBack : SatelliteDetailEvent()
}