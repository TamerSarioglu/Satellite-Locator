package com.tamersarioglu.satellitelocator.data.repository

import com.tamersarioglu.satellitelocator.data.local.datasource.SatelliteLocalDataSource
import com.tamersarioglu.satellitelocator.data.mapper.toDomain
import com.tamersarioglu.satellitelocator.data.mapper.toDomainList
import com.tamersarioglu.satellitelocator.data.mapper.toEntityList
import com.tamersarioglu.satellitelocator.data.mapper.toSatellitePositionList
import com.tamersarioglu.satellitelocator.data.remote.datasource.SatelliteRemoteDataSource
import com.tamersarioglu.satellitelocator.domain.model.Satellite
import com.tamersarioglu.satellitelocator.domain.model.SatelliteDetail
import com.tamersarioglu.satellitelocator.domain.model.SatellitePosition
import com.tamersarioglu.satellitelocator.domain.repository.SatelliteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SatelliteRepositoryImpl @Inject constructor(
    private val remoteDataSource: SatelliteRemoteDataSource,
    private val localDataSource: SatelliteLocalDataSource
) : SatelliteRepository {

    override suspend fun getAllSatellites(): Flow<List<Satellite>> {
        return localDataSource.getAllSatellites()
            .map { entities -> entities.toDomainList() }
    }

    override suspend fun refreshSatellites() {
        try {
            val remoteSatellites = remoteDataSource.getSatellites()
            val entities = remoteSatellites.toEntityList()

            localDataSource.insertSatellites(entities)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getSatelliteDetail(satelliteId: Int): SatelliteDetail? {
        val cachedDetail = localDataSource.getSatelliteDetailById(satelliteId)
        if (cachedDetail != null) {
            return cachedDetail.toDomain()
        }

        try {
            val remoteDetails = remoteDataSource.getSatelliteDetails()
            val detailEntities = remoteDetails.toEntityList()

            localDataSource.insertSatelliteDetails(detailEntities)
            return remoteDetails.find { it.id == satelliteId }?.toDomain()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPositionsForSatellite(satelliteId: Int): List<SatellitePosition> {
        return try {
            val positionsResponse = remoteDataSource.getPositions()
            val allPositions = positionsResponse.list.toSatellitePositionList()
            allPositions.filter { it.satelliteId == satelliteId }
        } catch (e: Exception) {
            emptyList()
        }
    }
}