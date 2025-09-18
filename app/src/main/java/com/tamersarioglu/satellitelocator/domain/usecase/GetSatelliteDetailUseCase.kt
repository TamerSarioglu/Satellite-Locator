package com.tamersarioglu.satellitelocator.domain.usecase

import com.tamersarioglu.satellitelocator.domain.model.SatelliteDetail
import com.tamersarioglu.satellitelocator.domain.repository.SatelliteRepository
import com.tamersarioglu.satellitelocator.utils.AppConfig
import javax.inject.Inject

class GetSatelliteDetailUseCase @Inject constructor(
    private val repository: SatelliteRepository
) {

    suspend operator fun invoke(satelliteId: Int): Result<SatelliteDetail> {
        return try {
            val detail = repository.getSatelliteDetail(satelliteId)
            if (detail != null) {
                Result.success(detail)
            } else {
                Result.failure(IllegalArgumentException("${AppConfig.ERROR_SATELLITE_DETAIL_NOT_FOUND}: $satelliteId"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}