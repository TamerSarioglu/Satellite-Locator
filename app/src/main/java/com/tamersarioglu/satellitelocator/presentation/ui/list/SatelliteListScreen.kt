package com.tamersarioglu.satellitelocator.presentation.ui.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tamersarioglu.satellitelocator.presentation.ui.component.EmptyState
import com.tamersarioglu.satellitelocator.presentation.ui.component.ErrorState
import com.tamersarioglu.satellitelocator.presentation.ui.component.LoadingState
import com.tamersarioglu.satellitelocator.presentation.ui.component.SatelliteList
import com.tamersarioglu.satellitelocator.presentation.ui.component.SearchBar
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SatelliteListScreen(
    onSatelliteClick: (Int) -> Unit,
) {
    val viewModel: SatelliteListViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel.uiEffect) {
        viewModel.uiEffect.collectLatest { effect ->
            when (effect) {
                is SatelliteListUiEffect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                is SatelliteListUiEffect.ShowSuccess -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                is SatelliteListUiEffect.NavigateToSatelliteDetail -> {
                    onSatelliteClick(effect.satelliteId)
                }
            }
        }
    }

    LaunchedEffect(uiState) {
        val currentState = uiState
        if (currentState is SatelliteListUiState.Error) {
            snackbarHostState.showSnackbar(currentState.message)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            if (uiState is SatelliteListUiState.Success) {
                SearchBar(
                    query = uiState.searchQuery,
                    onQueryChange = { query ->
                        viewModel.onEvent(SatelliteListEvent.SearchQueryChanged(query))
                    },
                    modifier = Modifier.padding(16.dp)
                )
            }

            when (val state = uiState) {
                is SatelliteListUiState.Loading -> {
                    LoadingState(
                        message = "Loading satellites..."
                    )
                }

                is SatelliteListUiState.Error -> {
                    ErrorState(
                        title = "Failed to Load Satellites",
                        message = "Unable to load satellite data. Please check your connection and try again."
                    )
                }

                is SatelliteListUiState.Success -> {
                    SatelliteListContent(
                        state = state,
                        viewModel = viewModel
                    )
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun SatelliteListContent(
    state: SatelliteListUiState.Success,
    viewModel: SatelliteListViewModel
) {
    when {
        state.displaySatellites.isEmpty() && state.searchQuery.isNotBlank() -> {
            EmptyState(
                icon = Icons.Filled.Search,
                title = "No Results Found",
                subtitle = "No satellites match \"${state.searchQuery}\". Try a different search term."
            )
        }

        state.displaySatellites.isEmpty() -> {
            EmptyState(
                icon = Icons.Filled.Info,
                title = "No Satellites Available",
                subtitle = "There are currently no satellites to display."
            )
        }

        else -> {
            SatelliteList(
                satellites = state.displaySatellites,
                onSatelliteClick = { satelliteId ->
                    viewModel.onEvent(
                        SatelliteListEvent.NavigateToSatelliteDetail(satelliteId)
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun SatelliteListScreenPreview() {
    MaterialTheme {
        SatelliteListScreen(
            onSatelliteClick = {}
        )
    }
}