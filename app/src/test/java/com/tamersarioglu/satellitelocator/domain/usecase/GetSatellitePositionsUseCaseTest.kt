package com.tamersarioglu.satellitelocator.domain.usecase

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.tamersarioglu.satellitelocator.domain.model.Position
import com.tamersarioglu.satellitelocator.domain.model.SatellitePosition
import com.tamersarioglu.satellitelocator.domain.repository.SatelliteRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetSatellitePositionsUseCaseTest {

    private val mockRepository = mockk<SatelliteRepository>()
    private lateinit var useCase: GetSatellitePositionsUseCase

    @Before
    fun setUp() {
        useCase = GetSatellitePositionsUseCase(mockRepository)
    }

    @Test
    fun `invoke emits positions correctly and loops`() = runTest {
        val satelliteId = 1
        val positions = listOf(
            Position(0.1, 0.2),
            Position(0.3, 0.4),
            Position(0.5, 0.6)
        )
        val satellitePosition = SatellitePosition(satelliteId, positions)

        coEvery { mockRepository.getPositionsForSatellite(satelliteId) } returns listOf(
            satellitePosition
        )

        useCase(satelliteId).test {
            assertThat(awaitItem()).isEqualTo(positions[0])
            assertThat(awaitItem()).isEqualTo(positions[1])
            assertThat(awaitItem()).isEqualTo(positions[2])
            assertThat(awaitItem()).isEqualTo(positions[0])
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `invoke throws exception when satellite not found`() = runTest {
        val satelliteId = 999
        coEvery { mockRepository.getPositionsForSatellite(satelliteId) } returns emptyList()

        useCase(satelliteId).test {
            awaitError().apply {
                assertThat(this).isInstanceOf(IllegalArgumentException::class.java)
                assertThat(message).contains("No positions found for satellite ID: 999")
            }
        }
    }

    @Test
    fun `invoke throws exception when positions list is empty`() = runTest {
        val satelliteId = 1
        val satellitePosition = SatellitePosition(satelliteId, emptyList())

        coEvery { mockRepository.getPositionsForSatellite(satelliteId) } returns listOf(
            satellitePosition
        )

        useCase(satelliteId).test {
            awaitError().apply {
                assertThat(this).isInstanceOf(IllegalArgumentException::class.java)
                assertThat(message).contains("Empty positions list for satellite ID: 1")
            }
        }
    }
}