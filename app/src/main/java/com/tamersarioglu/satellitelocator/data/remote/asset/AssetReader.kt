package com.tamersarioglu.satellitelocator.data.remote.asset

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AssetReader @Inject constructor(
    private val context: Context,
    private val json: Json
) {

    fun readAssetFile(fileName: String): String {
        return context.assets.open(fileName).use { inputStream ->
            inputStream.bufferedReader().use { reader ->
                reader.readText()
            }
        }
    }

    suspend fun <T> readJsonAsset(fileName: String, deserializer: DeserializationStrategy<T>): T {
        return withContext(Dispatchers.IO) {
            val jsonString = readAssetFile(fileName)
            json.decodeFromString(deserializer, jsonString)
        }
    }
}