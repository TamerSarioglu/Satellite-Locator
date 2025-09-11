package com.tamersarioglu.satellitelocator.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "satellites")
data class SatelliteEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val isActive: Boolean
)