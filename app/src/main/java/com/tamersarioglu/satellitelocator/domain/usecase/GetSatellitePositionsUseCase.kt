package com.tamersarioglu.satellitelocator.domain.usecase

import com.tamersarioglu.satellitelocator.domain.model.Position
import com.tamersarioglu.satellitelocator.domain.repository.SatelliteRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSatellitePositionsUseCase @Inject constructor(
    private val repository: SatelliteRepository
) {

    suspend operator fun invoke(satelliteId: Int): Flow<Position> = flow {
        try {
            val satellitePositions = repository.getPositionsForSatellite(satelliteId)

            if (satellitePositions.isNotEmpty()) {
                val positions = satellitePositions.first().positions

                // Emit each position every 3 seconds
                for (position in positions) {
                    emit(position)
                    delay(3000) // 3 seconds interval as required
                }

                // Loop back to first position to create continuous cycle
                while (true) {
                    for (position in positions) {
                        emit(position)
                        delay(3000)
                    }
                }
            }
        } catch (e: Exception) {
            // Could emit error state or handle gracefully
            throw e
        }
    }
}