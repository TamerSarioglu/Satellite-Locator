package com.tamersarioglu.satellitelocator.presentation.ui.detail

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.tamersarioglu.satellitelocator.domain.model.Position
import com.tamersarioglu.satellitelocator.domain.model.Satellite
import com.tamersarioglu.satellitelocator.domain.model.SatelliteDetail
import com.tamersarioglu.satellitelocator.domain.usecase.GetSatelliteDetailUseCase
import com.tamersarioglu.satellitelocator.domain.usecase.GetSatellitePositionsUseCase
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
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
class SatelliteDetailViewModelTest {

    private val mockGetSatellitesUseCase = mockk<GetSatellitesUseCase>()
    private val mockGetSatelliteDetailUseCase = mockk<GetSatelliteDetailUseCase>()
    private val mockGetSatellitePositionsUseCase = mockk<GetSatellitePositionsUseCase>()
    private lateinit var viewModel: SatelliteDetailViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val mockSatellite = Satellite(1, "Starship-1", true)
    private val mockSatelliteDetail = SatelliteDetail(
        id = 1,
        costPerLaunch = 7200000,
        firstFlight = LocalDate.parse("2021-12-01"),
        height = 118,
        mass = 1167000
    )
    private val mockPosition = Position(0.1, 0.2)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SatelliteDetailViewModel(
            mockGetSatellitesUseCase,
            mockGetSatelliteDetailUseCase,
            mockGetSatellitePositionsUseCase
        )
    }

    @Test
    fun `loadSatelliteDetail emits success when data loads successfully`() = runTest {
        val satelliteId = 1
        coEvery { mockGetSatellitesUseCase() } returns flowOf(listOf(mockSatellite))
        coEvery { mockGetSatelliteDetailUseCase(satelliteId) } returns Result.success(
            mockSatelliteDetail
        )
        coEvery { mockGetSatellitePositionsUseCase(satelliteId) } returns flowOf(mockPosition)

        viewModel.loadSatelliteDetail(satelliteId)

        viewModel.uiState.test {
            assertThat(awaitItem()).isInstanceOf(SatelliteDetailUiState.Loading::class.java)

            val successState = awaitItem() as SatelliteDetailUiState.Success
            assertThat(successState.satellite).isEqualTo(mockSatellite)
            assertThat(successState.satelliteDetail).isEqualTo(mockSatelliteDetail)
            assertThat(successState.currentPosition).isNull()

            val updatedState = awaitItem() as SatelliteDetailUiState.Success
            assertThat(updatedState.currentPosition).isEqualTo(mockPosition)
        }
    }

    @Test
    fun `loadSatelliteDetail emits error when satellite not found`() = runTest {
        val satelliteId = 999
        coEvery { mockGetSatellitesUseCase() } returns flowOf(listOf(mockSatellite))

        viewModel.loadSatelliteDetail(satelliteId)

        viewModel.uiState.test {
            assertThat(awaitItem()).isInstanceOf(SatelliteDetailUiState.Loading::class.java)

            val errorState = awaitItem() as SatelliteDetailUiState.Error
            assertThat(errorState.message).contains("Satellite not found")
        }
    }

    @Test
    fun `loadSatelliteDetail emits error when detail use case fails`() = runTest {
        val satelliteId = 1
        val errorMessage = "Detail not found"
        coEvery { mockGetSatellitesUseCase() } returns flowOf(listOf(mockSatellite))
        coEvery { mockGetSatelliteDetailUseCase(satelliteId) } returns Result.failure(
            Exception(
                errorMessage
            )
        )

        viewModel.loadSatelliteDetail(satelliteId)

        viewModel.uiState.test {
            assertThat(awaitItem()).isInstanceOf(SatelliteDetailUiState.Loading::class.java)

            val errorState = awaitItem() as SatelliteDetailUiState.Error
            assertThat(errorState.message).isEqualTo(errorMessage)
        }
    }
}