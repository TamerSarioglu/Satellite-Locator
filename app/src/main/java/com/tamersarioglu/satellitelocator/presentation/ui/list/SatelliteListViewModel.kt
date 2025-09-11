package com.tamersarioglu.satellitelocator.presentation.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamersarioglu.satellitelocator.domain.model.Satellite
import com.tamersarioglu.satellitelocator.domain.usecase.GetSatellitesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatelliteListViewModel @Inject constructor(
    private val getSatellitesUseCase: GetSatellitesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SatelliteListUiState>(SatelliteListUiState.Loading)
    val uiState: StateFlow<SatelliteListUiState> = _uiState.asStateFlow()

    init {
        observeSatellites()
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
            }
        }
    }

    private fun updateSatellitesData(satellites: List<Satellite>) {
        val currentState = _uiState.value
        val currentSearchQuery = (currentState as? SatelliteListUiState.Success)?.searchQuery ?: ""

        _uiState.value = SatelliteListUiState.Success(
            satellites = satellites,
            searchQuery = currentSearchQuery
        )
    }

    fun onSearchQueryChange(query: String) {
        val currentState = _uiState.value
        if (currentState is SatelliteListUiState.Success) {
            _uiState.value = currentState.copy(searchQuery = query)
        }
    }
}