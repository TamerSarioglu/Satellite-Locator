package com.tamersarioglu.satellitelocator.presentation.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamersarioglu.satellitelocator.domain.model.Satellite
import com.tamersarioglu.satellitelocator.domain.usecase.GetSatellitesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatelliteListViewModel @Inject constructor(
    private val getSatellitesUseCase: GetSatellitesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SatelliteListUiState>(SatelliteListUiState.Loading)
    val uiState: StateFlow<SatelliteListUiState> = _uiState.asStateFlow()

    private val _uiEffect = Channel<SatelliteListUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    init {
        observeSatellites()
    }

    fun onEvent(event: SatelliteListEvent) {
        when (event) {
            is SatelliteListEvent.SearchQueryChanged -> handleSearchQueryChange(event.query)
            is SatelliteListEvent.RetryLoading -> handleRetryLoading()
            is SatelliteListEvent.NavigateToSatelliteDetail -> handleNavigateToSatelliteDetail(event.satelliteId)
        }
    }

    private fun observeSatellites() {
        viewModelScope.launch {
            try {
                getSatellitesUseCase().collect { satellites ->
                    updateSatellitesData(satellites)
                }
            } catch (error: Exception) {
                _uiState.value = SatelliteListUiState.Error(
                    error.message ?: "Failed to load satellites"
                )
                sendUiEffect(
                    SatelliteListUiEffect.ShowError(
                        error.message ?: "Failed to load satellites"
                    )
                )
            }
        }
    }

    private fun handleSearchQueryChange(query: String) {
        val currentState = _uiState.value
        if (currentState is SatelliteListUiState.Success) {
            _uiState.value = currentState.copy(
                searchQuery = query,
                displaySatellites = filterSatellites(currentState.satellites, query)
            )
        }
    }

    private fun handleRetryLoading() {
        _uiState.value = SatelliteListUiState.Loading
        observeSatellites()
    }

    private fun handleNavigateToSatelliteDetail(satelliteId: Int) {
        sendUiEffect(SatelliteListUiEffect.NavigateToSatelliteDetail(satelliteId))
    }

    private fun updateSatellitesData(satellites: List<Satellite>) {
        val currentState = _uiState.value
        val currentSearchQuery = (currentState as? SatelliteListUiState.Success)?.searchQuery ?: ""

        _uiState.value = SatelliteListUiState.Success(
            satellites = satellites,
            displaySatellites = filterSatellites(satellites, currentSearchQuery),
            searchQuery = currentSearchQuery
        )
    }

    private fun filterSatellites(satellites: List<Satellite>, query: String): List<Satellite> {
        return if (query.isBlank()) {
            satellites
        } else {
            val trimmedQuery = query.trim().lowercase()
            satellites.filter { satellite ->
                satellite.name.lowercase().contains(trimmedQuery)
            }
        }
    }

    private fun sendUiEffect(effect: SatelliteListUiEffect) {
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }
}