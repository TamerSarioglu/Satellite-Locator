package com.tamersarioglu.satellitelocator.presentation.ui.list

sealed class SatelliteListEvent {
    data class SearchQueryChanged(val query: String) : SatelliteListEvent()
    object RetryLoading : SatelliteListEvent()
    data class NavigateToSatelliteDetail(val satelliteId: Int) : SatelliteListEvent()
}