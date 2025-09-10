package com.tamersarioglu.satellitelocator.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tamersarioglu.satellitelocator.data.local.dao.SatelliteDao
import com.tamersarioglu.satellitelocator.data.local.dao.SatelliteDetailDao
import com.tamersarioglu.satellitelocator.data.local.entity.SatelliteEntity
import com.tamersarioglu.satellitelocator.data.local.entity.SatelliteDetailEntity

@Database(
    entities = [
        SatelliteEntity::class,
        SatelliteDetailEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SatelliteDatabase : RoomDatabase() {

    abstract fun satelliteDao(): SatelliteDao
    abstract fun satelliteDetailDao(): SatelliteDetailDao
}