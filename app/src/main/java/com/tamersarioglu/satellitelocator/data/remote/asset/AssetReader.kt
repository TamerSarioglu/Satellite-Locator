package com.tamersarioglu.satellitelocator.data.remote.asset

import android.content.Context
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AssetReader @Inject constructor(
    private val context: Context,
    private val json: Json
) {

    suspend fun readAssetFile(fileName: String): String {
        return context.assets.open(fileName).use { inputStream ->
            inputStream.bufferedReader().use { reader ->
                reader.readText()
            }
        }
    }

    suspend fun <T> readJsonAsset(fileName: String, deserializer: DeserializationStrategy<T>): T {
        val jsonString = readAssetFile(fileName)
        return json.decodeFromString(deserializer, jsonString)
    }
}