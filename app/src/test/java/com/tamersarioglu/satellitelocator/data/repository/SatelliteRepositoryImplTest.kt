package com.tamersarioglu.satellitelocator.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.tamersarioglu.satellitelocator.data.local.datasource.SatelliteLocalDataSource
import com.tamersarioglu.satellitelocator.data.local.entity.SatelliteDetailEntity
import com.tamersarioglu.satellitelocator.data.local.entity.SatelliteEntity
import com.tamersarioglu.satellitelocator.data.model.PositionDto
import com.tamersarioglu.satellitelocator.data.model.PositionsResponse
import com.tamersarioglu.satellitelocator.data.model.SatelliteDetailDto
import com.tamersarioglu.satellitelocator.data.model.SatelliteDto
import com.tamersarioglu.satellitelocator.data.model.SatellitePositionDto
import com.tamersarioglu.satellitelocator.data.remote.datasource.SatelliteRemoteDataSource
import com.tamersarioglu.satellitelocator.domain.model.Satellite
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class SatelliteRepositoryImplTest {

    private val mockRemoteDataSource = mockk<SatelliteRemoteDataSource>()
    private val mockLocalDataSource = mockk<SatelliteLocalDataSource>()
    private lateinit var repository: SatelliteRepositoryImpl

    private val mockSatelliteDto = SatelliteDto(1, true, "Starship-1")
    private val mockSatelliteEntity = SatelliteEntity(1, "Starship-1", true)
    private val mockSatelliteDetailDto = SatelliteDetailDto(1, 7200000, "2021-12-01", 118, 1167000)
    private val mockSatelliteDetailEntity =
        SatelliteDetailEntity(1, 7200000, "2021-12-01", 118, 1167000)

    @Before
    fun setUp() {
        repository = SatelliteRepositoryImpl(mockRemoteDataSource, mockLocalDataSource)
    }

    @Test
    fun `getAllSatellites returns local data as flow`() = runTest {
        val expectedSatellites = listOf(Satellite(1, "Starship-1", true))
        coEvery { mockLocalDataSource.getAllSatellites() } returns flowOf(listOf(mockSatelliteEntity))

        val result = repository.getAllSatellites()

        result.test {
            val satellites = awaitItem()
            assertThat(satellites).hasSize(1)
            assertThat(satellites[0].name).isEqualTo("Starship-1")
            assertThat(satellites[0].isActive).isTrue()
            awaitComplete()
        }
    }

    @Test
    fun `refreshSatellites fetches from remote and caches locally`() = runTest {
        coEvery { mockRemoteDataSource.getSatellites() } returns listOf(mockSatelliteDto)
        coEvery { mockLocalDataSource.insertSatellites(any()) } returns Unit

        repository.refreshSatellites()

        coVerify { mockRemoteDataSource.getSatellites() }
        coVerify { mockLocalDataSource.insertSatellites(any()) }
    }

    @Test
    fun `getSatelliteDetail uses cache first then fetches from remote`() = runTest {
        val satelliteId = 1

        coEvery { mockLocalDataSource.getSatelliteDetailById(satelliteId) } returns null
        coEvery { mockRemoteDataSource.getSatelliteDetails() } returns listOf(mockSatelliteDetailDto)
        coEvery { mockLocalDataSource.insertSatelliteDetails(any()) } returns Unit

        val result = repository.getSatelliteDetail(satelliteId)

        assertThat(result).isNotNull()
        assertThat(result?.id).isEqualTo(1)
        assertThat(result?.costPerLaunch).isEqualTo(7200000)
        coVerify { mockRemoteDataSource.getSatelliteDetails() }
        coVerify { mockLocalDataSource.insertSatelliteDetails(any()) }
    }

    @Test
    fun `getSatelliteDetail returns cached data when available`() = runTest {
        val satelliteId = 1
        coEvery { mockLocalDataSource.getSatelliteDetailById(satelliteId) } returns mockSatelliteDetailEntity

        val result = repository.getSatelliteDetail(satelliteId)

        assertThat(result).isNotNull()
        assertThat(result?.id).isEqualTo(1)
        coVerify(exactly = 0) { mockRemoteDataSource.getSatelliteDetails() }
    }

    @Test
    fun `getPositionsForSatellite returns filtered positions`() = runTest {
        val satelliteId = 1
        val positionsResponse = PositionsResponse(
            list = listOf(
                SatellitePositionDto(
                    id = "1",
                    positions = listOf(
                        PositionDto(0.1, 0.2),
                        PositionDto(0.3, 0.4)
                    )
                ),
                SatellitePositionDto(
                    id = "2",
                    positions = listOf(
                        PositionDto(0.5, 0.6)
                    )
                )
            )
        )

        coEvery { mockRemoteDataSource.getPositions() } returns positionsResponse

        val result = repository.getPositionsForSatellite(satelliteId)

        assertThat(result).hasSize(1)
        assertThat(result[0].satelliteId).isEqualTo(satelliteId)
        assertThat(result[0].positions).hasSize(2)
    }
}