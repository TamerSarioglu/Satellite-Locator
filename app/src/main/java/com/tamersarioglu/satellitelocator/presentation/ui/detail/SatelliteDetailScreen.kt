package com.tamersarioglu.satellitelocator.presentation.ui.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tamersarioglu.satellitelocator.presentation.ui.component.ErrorState
import com.tamersarioglu.satellitelocator.presentation.ui.component.LoadingState
import com.tamersarioglu.satellitelocator.presentation.ui.component.SatelliteDetailContent
import com.tamersarioglu.satellitelocator.presentation.ui.component.SatelliteDetailTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SatelliteDetailScreen(
    satelliteId: Int,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SatelliteDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(satelliteId) {
        viewModel.loadSatelliteDetail(satelliteId)
    }

    LaunchedEffect(uiState) {
        val currentState = uiState
        if (currentState is SatelliteDetailUiState.Error) {
            snackbarHostState.showSnackbar(currentState.message)
        }
    }

    Scaffold(
        topBar = {
            SatelliteDetailTopBar(
                title = uiState.satellite?.name ?: "Loading...",
                onBackClick = onBackClick
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier
    ) { paddingValues ->
        when (val state = uiState) {
            is SatelliteDetailUiState.Loading -> {
                LoadingState(
                    message = "Loading satellite details...",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            is SatelliteDetailUiState.Error -> {
                ErrorState(
                    title = "Failed to Load",
                    message = "Failed to load satellite details. Please try again.",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            is SatelliteDetailUiState.Success -> {
                SatelliteDetailContent(
                    satellite = state.satellite,
                    satelliteDetail = state.satelliteDetail,
                    currentPosition = state.currentPosition,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
        }
    }
}

@Preview
@Composable
private fun SatelliteDetailScreenPreview() {
    MaterialTheme {
        SatelliteDetailScreen(
            satelliteId = 1,
            onBackClick = {}
        )
    }
}