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

                for (position in positions) {
                    emit(position)
                    delay(3000)
                }

                while (true) {
                    for (position in positions) {
                        emit(position)
                        delay(3000)
                    }
                }
            }
        } catch (e: Exception) {
            throw e
        }
    }
}