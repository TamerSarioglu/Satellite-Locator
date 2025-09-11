package com.tamersarioglu.satellitelocator.domain.usecase

import com.tamersarioglu.satellitelocator.domain.model.Position
import com.tamersarioglu.satellitelocator.domain.repository.SatelliteRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.isActive
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext

@Singleton
class GetSatellitePositionsUseCase @Inject constructor(
    private val repository: SatelliteRepository
) {

    operator fun invoke(satelliteId: Int): Flow<Position> = flow {
        val satellitePositions = repository.getPositionsForSatellite(satelliteId)
        if (satellitePositions.isEmpty()) {
            throw IllegalArgumentException("No positions found for satellite ID: $satelliteId")
        }

        val positions = satellitePositions.first().positions
        if (positions.isEmpty()) {
            throw IllegalArgumentException("Empty positions list for satellite ID: $satelliteId")
        }

        var currentIndex = 0
        while (coroutineContext.isActive) {
            emit(positions[currentIndex])
            delay(3000)
            currentIndex = (currentIndex + 1) % positions.size
        }
    }
}