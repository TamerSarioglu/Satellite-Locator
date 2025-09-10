package com.tamersarioglu.satellitelocator.domain.usecase

import com.tamersarioglu.satellitelocator.domain.model.SatelliteDetail
import com.tamersarioglu.satellitelocator.domain.repository.SatelliteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSatelliteDetailUseCase @Inject constructor(
    private val repository: SatelliteRepository
) {

    suspend operator fun invoke(satelliteId: Int): Result<SatelliteDetail> {
        return try {
            val detail = repository.getSatelliteDetail(satelliteId)
            if (detail != null) {
                Result.success(detail)
            } else {
                Result.failure(SatelliteDetailNotFoundException("Satellite detail not found for ID: $satelliteId"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
//TODO: Gerekli mi?
class SatelliteDetailNotFoundException(message: String) : Exception(message)