package com.tamersarioglu.satellitelocator.data.local.datasource

import com.tamersarioglu.satellitelocator.data.local.dao.SatelliteDao
import com.tamersarioglu.satellitelocator.data.local.dao.SatelliteDetailDao
import com.tamersarioglu.satellitelocator.data.local.entity.SatelliteDetailEntity
import com.tamersarioglu.satellitelocator.data.local.entity.SatelliteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SatelliteLocalDataSource @Inject constructor(
    private val satelliteDao: SatelliteDao,
    private val satelliteDetailDao: SatelliteDetailDao
) {

    fun getAllSatellites(): Flow<List<SatelliteEntity>> {
        return satelliteDao.getAllSatellites()
    }

    suspend fun insertSatellites(satellites: List<SatelliteEntity>) {
        satelliteDao.insertSatellites(satellites)
    }

    suspend fun getSatelliteDetailById(id: Int): SatelliteDetailEntity? {
        return satelliteDetailDao.getSatelliteDetailById(id)
    }

    suspend fun insertSatelliteDetails(satelliteDetails: List<SatelliteDetailEntity>) {
        satelliteDetailDao.insertSatelliteDetails(satelliteDetails)
    }
}