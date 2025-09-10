package com.tamersarioglu.satellitelocator.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tamersarioglu.satellitelocator.data.local.entity.SatelliteDetailEntity

@Dao
interface SatelliteDetailDao {

    @Query("SELECT * FROM satellite_details WHERE id = :id")
    suspend fun getSatelliteDetailById(id: Int): SatelliteDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSatelliteDetail(satelliteDetail: SatelliteDetailEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSatelliteDetails(satelliteDetails: List<SatelliteDetailEntity>)

    @Query("DELETE FROM satellite_details WHERE id = :id")
    suspend fun deleteSatelliteDetailById(id: Int)

    @Query("DELETE FROM satellite_details")
    suspend fun deleteAllSatelliteDetails()
}