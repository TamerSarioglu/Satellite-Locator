package com.tamersarioglu.satellitelocator.data.repository

import com.tamersarioglu.satellitelocator.data.local.datasource.SatelliteLocalDataSource
import com.tamersarioglu.satellitelocator.data.mapper.PositionMapper
import com.tamersarioglu.satellitelocator.data.mapper.SatelliteDetailMapper
import com.tamersarioglu.satellitelocator.data.mapper.SatelliteMapper
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
    private val localDataSource: SatelliteLocalDataSource,
    private val satelliteMapper: SatelliteMapper,
    private val satelliteDetailMapper: SatelliteDetailMapper,
    private val positionMapper: PositionMapper
) : SatelliteRepository {

    override fun getAllSatellites(): Flow<List<Satellite>> {
        return localDataSource.getAllSatellites()
            .map { entities ->
                satelliteMapper.entitiesToDomainList(entities)
            }
    }

    override suspend fun refreshSatellites() {
        try {
            val remoteSatellites = remoteDataSource.getSatellites()
            val entities = satelliteMapper.dtosToEntityList(remoteSatellites)
            localDataSource.insertSatellites(entities)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getSatelliteDetail(satelliteId: Int): SatelliteDetail? {
        val cachedDetail = localDataSource.getSatelliteDetailById(satelliteId)
        if (cachedDetail != null) {
            return satelliteDetailMapper.toDomain(cachedDetail)
        }

        try {
            val remoteDetails = remoteDataSource.getSatelliteDetails()
            val detailEntities = satelliteDetailMapper.dtosToEntityList(remoteDetails)
            localDataSource.insertSatelliteDetails(detailEntities)

            return remoteDetails
                .find { it.id == satelliteId }
                ?.let { satelliteDetailMapper.toDomain(it) }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getPositionsForSatellite(satelliteId: Int): List<SatellitePosition> {
        return try {
            val positionsResponse = remoteDataSource.getPositions()
            val allPositions = positionMapper.toSatellitePositionList(positionsResponse.list)

            allPositions.filter { it.satelliteId == satelliteId }
        } catch (e: Exception) {
            emptyList()
        }
    }
}