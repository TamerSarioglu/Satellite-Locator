package com.tamersarioglu.satellitelocator.presentation.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamersarioglu.satellitelocator.domain.usecase.GetSatellitesUseCase
import com.tamersarioglu.satellitelocator.domain.usecase.SearchSatellitesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatelliteListViewModel @Inject constructor(
    private val getSatellitesUseCase: GetSatellitesUseCase,
    private val searchSatellitesUseCase: SearchSatellitesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SatelliteListUiState())
    val uiState: StateFlow<SatelliteListUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        observeSatellites()
        loadSatellites()
    }

    private fun observeSatellites() {
        viewModelScope.launch {
            combine(
                getSatellitesUseCase(),
                _searchQuery
            ) { satellites, query ->
                val filteredSatellites = searchSatellitesUseCase(satellites, query)

                _uiState.value = _uiState.value.copy(
                    satellites = satellites,
                    filteredSatellites = filteredSatellites,
                    searchQuery = query,
                    isLoading = false,
                    errorMessage = null
                )
            }.catch { exception ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = exception.message ?: "Unknown error occurred"
                )
            }.collect {}
        }
    }

    private fun loadSatellites() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                getSatellitesUseCase.refresh()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Failed to load satellites"
                )
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onRefresh() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isRefreshing = true)
            try {
                getSatellitesUseCase.refresh()
                _uiState.value = _uiState.value.copy(isRefreshing = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isRefreshing = false,
                    errorMessage = e.message ?: "Failed to refresh satellites"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}