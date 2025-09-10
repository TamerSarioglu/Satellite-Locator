package com.tamersarioglu.satellitelocator.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamersarioglu.satellitelocator.domain.usecase.GetSatelliteDetailUseCase
import com.tamersarioglu.satellitelocator.domain.usecase.GetSatellitePositionsUseCase
import com.tamersarioglu.satellitelocator.domain.usecase.GetSatellitesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatelliteDetailViewModel @Inject constructor(
    private val getSatellitesUseCase: GetSatellitesUseCase,
    private val getSatelliteDetailUseCase: GetSatelliteDetailUseCase,
    private val getSatellitePositionsUseCase: GetSatellitePositionsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SatelliteDetailUiState())
    val uiState: StateFlow<SatelliteDetailUiState> = _uiState.asStateFlow()

    fun loadSatelliteDetail(satelliteId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            try {
                val satellite = getSatellitesUseCase()
                    .first()
                    .find { it.id == satelliteId }

                if (satellite == null) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Satellite not found"
                    )
                    return@launch
                }

                val detailResult = getSatelliteDetailUseCase(satelliteId)
                if (detailResult.isFailure) {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = detailResult.exceptionOrNull()?.message
                            ?: "Failed to load detail"
                    )
                    return@launch
                }

                val satelliteDetail = detailResult.getOrNull()
                _uiState.value = _uiState.value.copy(
                    satellite = satellite,
                    satelliteDetail = satelliteDetail,
                    isLoading = false
                )

                startPositionUpdates(satelliteId)

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    private fun startPositionUpdates(satelliteId: Int) {
        viewModelScope.launch {
            getSatellitePositionsUseCase(satelliteId)
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        errorMessage = "Failed to load position updates: ${e.message}"
                    )
                }
                .collect { position ->
                    _uiState.value = _uiState.value.copy(currentPosition = position)
                }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}