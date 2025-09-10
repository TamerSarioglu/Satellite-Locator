package com.tamersarioglu.satellitelocator.presentation.model

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String, val throwable: Throwable? = null) : UiState<Nothing>()
}

data class SatelliteListUiState(
    val satellites: List<SatelliteUiModel> = emptyList(),
    val filteredSatellites: List<SatelliteUiModel> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

data class SatelliteDetailUiState(
    val satelliteDetail: SatelliteDetailUiModel? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)