package com.tamersarioglu.satellitelocator.domain.usecase

import com.tamersarioglu.satellitelocator.domain.model.Position
import com.tamersarioglu.satellitelocator.domain.repository.SatelliteRepository
import com.tamersarioglu.satellitelocator.utils.AppConfig
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import kotlin.coroutines.coroutineContext
import javax.inject.Inject

class GetSatellitePositionsUseCase @Inject constructor(
    private val repository: SatelliteRepository
) {

    operator fun invoke(
        satelliteId: Int,
        updateIntervalMs: Long = AppConfig.POSITION_UPDATE_INTERVAL_MS
    ): Flow<Position> = flow {
        val satellitePositions = repository.getPositionsForSatellite(satelliteId)
        if (satellitePositions.isEmpty()) {
            throw IllegalArgumentException("${AppConfig.ERROR_NO_POSITIONS_FOUND}: $satelliteId")
        }

        val positions = satellitePositions.first().positions
        if (positions.isEmpty()) {
            throw IllegalArgumentException("${AppConfig.ERROR_EMPTY_POSITIONS_LIST}: $satelliteId")
        }

        var currentIndex = 0
        while (coroutineContext.isActive) {
            emit(positions[currentIndex])
            delay(updateIntervalMs)
            currentIndex = (currentIndex + 1) % positions.size
        }
    }
}