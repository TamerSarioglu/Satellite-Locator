package com.tamersarioglu.satellitelocator.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.tamersarioglu.satellitelocator.domain.model.SatelliteDetail
import com.tamersarioglu.satellitelocator.domain.repository.SatelliteRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class GetSatelliteDetailUseCaseTest {

    private val mockRepository = mockk<SatelliteRepository>()
    private lateinit var useCase: GetSatelliteDetailUseCase

    @Before
    fun setUp() {
        useCase = GetSatelliteDetailUseCase(mockRepository)
    }

    @Test
    fun `invoke returns success when detail found`() = runTest {
        val satelliteId = 1
        val expectedDetail = SatelliteDetail(
            id = 1,
            costPerLaunch = 7200000,
            firstFlight = LocalDate.parse("2021-12-01"),
            height = 118,
            mass = 1167000
        )

        coEvery { mockRepository.getSatelliteDetail(satelliteId) } returns expectedDetail

        val result = useCase(satelliteId)

        assertThat(result.isSuccess).isTrue()
        assertThat(result.getOrNull()).isEqualTo(expectedDetail)
    }

    @Test
    fun `invoke returns failure when detail not found`() = runTest {
        val satelliteId = 999
        coEvery { mockRepository.getSatelliteDetail(satelliteId) } returns null

        val result = useCase(satelliteId)

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(Exception::class.java)
        assertThat(result.exceptionOrNull()?.message).isEqualTo("Satellite detail not found for ID: 999")
    }

    @Test
    fun `invoke returns failure when repository throws exception`() = runTest {
        val satelliteId = 1
        val expectedException = RuntimeException("Network error")
        coEvery { mockRepository.getSatelliteDetail(satelliteId) } throws expectedException

        val result = useCase(satelliteId)

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isEqualTo(expectedException)
    }
}