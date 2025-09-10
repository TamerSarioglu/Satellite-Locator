package com.tamersarioglu.satellitelocator.data.remote.datasource

import com.tamersarioglu.satellitelocator.data.model.PositionsResponse
import com.tamersarioglu.satellitelocator.data.model.SatelliteDetailDto
import com.tamersarioglu.satellitelocator.data.model.SatelliteDto
import com.tamersarioglu.satellitelocator.data.remote.asset.AssetReader
import kotlinx.serialization.builtins.ListSerializer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SatelliteRemoteDataSource @Inject constructor(
    private val assetReader: AssetReader
) {

    suspend fun getSatellites(): List<SatelliteDto> {
        return assetReader.readJsonAsset(
            fileName = "satellites.json",
            deserializer = ListSerializer(SatelliteDto.serializer())
        )
    }

    suspend fun getSatelliteDetails(): List<SatelliteDetailDto> {
        return assetReader.readJsonAsset(
            fileName = "satellite-detail.json",
            deserializer = ListSerializer(SatelliteDetailDto.serializer())
        )
    }

    suspend fun getPositions(): PositionsResponse {
        return assetReader.readJsonAsset(
            fileName = "positions.json",
            deserializer = PositionsResponse.serializer()
        )
    }
}