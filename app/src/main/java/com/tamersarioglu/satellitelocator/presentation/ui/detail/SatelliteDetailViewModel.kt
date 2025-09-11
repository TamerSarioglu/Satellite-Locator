package com.tamersarioglu.satellitelocator.presentation.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamersarioglu.satellitelocator.domain.model.Position
import com.tamersarioglu.satellitelocator.domain.model.Satellite
import com.tamersarioglu.satellitelocator.domain.model.SatelliteDetail
import com.tamersarioglu.satellitelocator.domain.usecase.GetSatelliteDetailUseCase
import com.tamersarioglu.satellitelocator.domain.usecase.GetSatellitePositionsUseCase
import com.tamersarioglu.satellitelocator.domain.usecase.GetSatellitesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatelliteDetailViewModel @Inject constructor(
    private val getSatellitesUseCase: GetSatellitesUseCase,
    private val getSatelliteDetailUseCase: GetSatelliteDetailUseCase,
    private val getSatellitePositionsUseCase: GetSatellitePositionsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<SatelliteDetailUiState>(SatelliteDetailUiState.Loading)
    val uiState: StateFlow<SatelliteDetailUiState> = _uiState.asStateFlow()

    fun loadSatelliteDetail(satelliteId: Int) {
        viewModelScope.launch {
            _uiState.value = SatelliteDetailUiState.Loading

            loadSatelliteData(satelliteId)
                .onSuccess { (satellite, detail) ->
                    _uiState.value = SatelliteDetailUiState.Success(satellite, detail)
                    startPositionUpdates(satelliteId)
                }
                .onFailure { error ->
                    _uiState.value = SatelliteDetailUiState.Error(
                        error.message ?: "Unknown error occurred"
                    )
                }
        }
    }

    private suspend fun loadSatelliteData(satelliteId: Int): Result<Pair<Satellite, SatelliteDetail>> {
        return runCatching {
            val satellite = findSatelliteById(satelliteId)
            val detailResult = getSatelliteDetailUseCase(satelliteId)

            if (detailResult.isFailure) {
                throw detailResult.exceptionOrNull() ?: Exception("Failed to load satellite detail")
            }

            val satelliteDetail = detailResult.getOrThrow()
            satellite to satelliteDetail
        }
    }

    private suspend fun findSatelliteById(satelliteId: Int): Satellite {
        return getSatellitesUseCase()
            .first()
            .find { it.id == satelliteId }
            ?: throw Exception("Satellite not found")
    }

    private fun startPositionUpdates(satelliteId: Int) {
        viewModelScope.launch {
            runCatching {
                getSatellitePositionsUseCase(satelliteId).collect { position ->
                    updateCurrentPosition(position)
                }
            }.onFailure { error ->
                _uiState.value = SatelliteDetailUiState.Error(
                    "Failed to load position updates: ${error.message}"
                )
            }
        }
    }

    private fun updateCurrentPosition(position: Position) {
        val currentState = _uiState.value
        if (currentState is SatelliteDetailUiState.Success) {
            _uiState.value = currentState.copy(currentPosition = position)
        }
    }
}