package com.tamersarioglu.satellitelocator.data.remote.datasource

import com.tamersarioglu.satellitelocator.data.model.PositionsResponse
import com.tamersarioglu.satellitelocator.data.model.SatelliteDetailDto
import com.tamersarioglu.satellitelocator.data.model.SatelliteDto
import com.tamersarioglu.satellitelocator.data.remote.asset.AssetReader
import com.tamersarioglu.satellitelocator.utils.AppConfig
import kotlinx.serialization.builtins.ListSerializer
import javax.inject.Inject

class SatelliteRemoteDataSource @Inject constructor(
    private val assetReader: AssetReader
) {

    suspend fun getSatellites(): List<SatelliteDto> {
        return assetReader.readJsonAsset(
            fileName = AppConfig.FILE_SATELLITES_JSON,
            deserializer = ListSerializer(SatelliteDto.serializer())
        )
    }

    suspend fun getSatelliteDetails(): List<SatelliteDetailDto> {
        return assetReader.readJsonAsset(
            fileName = AppConfig.FILE_SATELLITE_DETAIL_JSON,
            deserializer = ListSerializer(SatelliteDetailDto.serializer())
        )
    }

    suspend fun getPositions(): PositionsResponse {
        return assetReader.readJsonAsset(
            fileName = AppConfig.FILE_POSITIONS_JSON,
            deserializer = PositionsResponse.serializer()
        )
    }
}