package com.tamersarioglu.satellitelocator.domain.usecase

import com.tamersarioglu.satellitelocator.domain.model.Satellite
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchSatellitesUseCase @Inject constructor() {

    operator fun invoke(
        satellites: List<Satellite>,
        query: String
    ): List<Satellite> {
        if (query.isBlank()) return satellites

        val trimmedQuery = query.trim().lowercase()
        return satellites.filter { satellite ->
            satellite.name.lowercase().contains(trimmedQuery)
        }
    }
}