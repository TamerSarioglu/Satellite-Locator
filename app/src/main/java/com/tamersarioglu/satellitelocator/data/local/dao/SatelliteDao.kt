package com.tamersarioglu.satellitelocator.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tamersarioglu.satellitelocator.data.local.entity.SatelliteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SatelliteDao {

    @Query("SELECT * FROM satellites ORDER BY name ASC")
    fun getAllSatellites(): Flow<List<SatelliteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSatellites(satellites: List<SatelliteEntity>)
}