package com.tamersarioglu.satellitelocator.presentation.ui.list

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.tamersarioglu.satellitelocator.domain.model.Satellite
import com.tamersarioglu.satellitelocator.domain.usecase.GetSatellitesUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SatelliteListViewModelTest {

    private val mockGetSatellitesUseCase = mockk<GetSatellitesUseCase>()
    private lateinit var viewModel: SatelliteListViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val mockSatellites = listOf(
        Satellite(1, "Starship-1", true),
        Satellite(2, "Dragon-1", false),
        Satellite(3, "Starship-3", true)
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `uiState emits success when satellites are loaded successfully`() = runTest {
        coEvery { mockGetSatellitesUseCase() } returns flowOf(mockSatellites)

        viewModel = SatelliteListViewModel(mockGetSatellitesUseCase)

        viewModel.uiState.test {
            assertThat(awaitItem()).isInstanceOf(SatelliteListUiState.Loading::class.java)

            val successState = awaitItem() as SatelliteListUiState.Success
            assertThat(successState.satellites).isEqualTo(mockSatellites)
            assertThat(successState.searchQuery).isEmpty()
            assertThat(successState.displaySatellites).isEqualTo(mockSatellites)
        }
    }

    @Test
    fun `uiState emits error when satellites loading fails`() = runTest {
        val errorMessage = "Network error occurred"
        coEvery { mockGetSatellitesUseCase() } throws RuntimeException(errorMessage)

        viewModel = SatelliteListViewModel(mockGetSatellitesUseCase)

        viewModel.uiState.test {
            assertThat(awaitItem()).isInstanceOf(SatelliteListUiState.Loading::class.java)

            val errorState = awaitItem() as SatelliteListUiState.Error
            assertThat(errorState.message).isEqualTo(errorMessage)
        }
    }

    @Test
    fun `onSearchQueryChange updates search query in success state`() = runTest {
        coEvery { mockGetSatellitesUseCase() } returns flowOf(mockSatellites)
        viewModel = SatelliteListViewModel(mockGetSatellitesUseCase)

        viewModel.uiState.test {
            skipItems(2)

            viewModel.onEvent(SatelliteListEvent.SearchQueryChanged("Starship"))

            val updatedState = awaitItem() as SatelliteListUiState.Success
            assertThat(updatedState.searchQuery).isEqualTo("Starship")
            assertThat(updatedState.satellites).isEqualTo(mockSatellites)
            assertThat(updatedState.displaySatellites).hasSize(2)
            assertThat(updatedState.displaySatellites.map { it.name })
                .containsExactly("Starship-1", "Starship-3")
        }
    }
}