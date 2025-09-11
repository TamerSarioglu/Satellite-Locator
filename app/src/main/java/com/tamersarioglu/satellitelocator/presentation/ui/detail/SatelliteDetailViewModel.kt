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
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val _uiEffect = Channel<SatelliteDetailUiEffect>()
    val uiEffect = _uiEffect.receiveAsFlow()

    private var positionUpdateJob: Job? = null
    private var currentSatelliteId: Int? = null

    fun onEvent(event: SatelliteDetailEvent) {
        when (event) {
            is SatelliteDetailEvent.RefreshPosition -> handleRefreshPosition()
            is SatelliteDetailEvent.RetryLoading -> handleRetryLoading()
            is SatelliteDetailEvent.NavigateBack -> handleNavigateBack()
        }
    }

    fun loadSatelliteDetail(satelliteId: Int) {
        currentSatelliteId = satelliteId
        viewModelScope.launch {
            _uiState.value = SatelliteDetailUiState.Loading

            loadSatelliteData(satelliteId).fold(
                onSuccess = { (satellite, detail) ->
                    _uiState.value = SatelliteDetailUiState.Success(satellite, detail)
                    startPositionUpdates(satelliteId)
                },
                onFailure = { error ->
                    _uiState.value = SatelliteDetailUiState.Error(
                        error.message ?: "Unknown error occurred"
                    )
                    sendUiEffect(
                        SatelliteDetailUiEffect.ShowError(
                            error.message ?: "Failed to load satellite detail"
                        )
                    )
                }
            )
        }
    }

    private fun handleRefreshPosition() {
        currentSatelliteId?.let { satelliteId ->
            positionUpdateJob?.cancel()
            startPositionUpdates(satelliteId)
            sendUiEffect(SatelliteDetailUiEffect.ShowSuccess("Position refreshed"))
        }
    }

    private fun handleRetryLoading() {
        currentSatelliteId?.let { satelliteId ->
            loadSatelliteDetail(satelliteId)
        }
    }

    private fun handleNavigateBack() {
        sendUiEffect(SatelliteDetailUiEffect.NavigateBack)
    }

    private suspend fun loadSatelliteData(satelliteId: Int): Result<Pair<Satellite, SatelliteDetail>> {
        return try {
            val satellite = findSatelliteById(satelliteId)
            getSatelliteDetailUseCase(satelliteId).fold(
                onSuccess = { satelliteDetail ->
                    Result.success(satellite to satelliteDetail)
                },
                onFailure = { error ->
                    Result.failure(error)
                }
            )
        } catch (error: Exception) {
            Result.failure(error)
        }
    }

    private suspend fun findSatelliteById(satelliteId: Int): Satellite {
        return getSatellitesUseCase()
            .first()
            .find { it.id == satelliteId }
            ?: throw Exception("Satellite not found")
    }

    private fun startPositionUpdates(satelliteId: Int) {
        positionUpdateJob?.cancel()
        positionUpdateJob = viewModelScope.launch {
            try {
                getSatellitePositionsUseCase(satelliteId).collect { position ->
                    updateCurrentPosition(position)
                }
            } catch (error: Exception) {
                _uiState.value = SatelliteDetailUiState.Error(
                    "Failed to load position updates: ${error.message}"
                )
                sendUiEffect(
                    SatelliteDetailUiEffect.ShowError(
                        "Failed to load position updates: ${error.message}"
                    )
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

    private fun sendUiEffect(effect: SatelliteDetailUiEffect) {
        viewModelScope.launch {
            _uiEffect.send(effect)
        }
    }

    override fun onCleared() {
        super.onCleared()
        positionUpdateJob?.cancel()
    }
}