package com.tamersarioglu.satellitelocator.domain.usecase

import com.tamersarioglu.satellitelocator.domain.model.Satellite
import com.tamersarioglu.satellitelocator.domain.repository.SatelliteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSatellitesUseCase @Inject constructor(
    private val repository: SatelliteRepository
) {

    operator fun invoke(): Flow<List<Satellite>> {
        return repository.getAllSatellites()
    }

    suspend fun refresh() {
        repository.refreshSatellites()
    }
}