package com.tamersarioglu.satellitelocator.domain.repository

import com.tamersarioglu.satellitelocator.domain.model.Satellite
import com.tamersarioglu.satellitelocator.domain.model.SatelliteDetail
import com.tamersarioglu.satellitelocator.domain.model.SatellitePosition
import kotlinx.coroutines.flow.Flow

interface SatelliteRepository {

    fun getAllSatellites(): Flow<List<Satellite>>
    suspend fun refreshSatellites()
    suspend fun getSatelliteDetail(satelliteId: Int): SatelliteDetail?
    suspend fun getPositionsForSatellite(satelliteId: Int): List<SatellitePosition>
}