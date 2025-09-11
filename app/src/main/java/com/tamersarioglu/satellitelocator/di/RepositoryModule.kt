package com.tamersarioglu.satellitelocator.di

import com.tamersarioglu.satellitelocator.data.repository.SatelliteRepositoryImpl
import com.tamersarioglu.satellitelocator.domain.repository.SatelliteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSatelliteRepository(
        satelliteRepositoryImpl: SatelliteRepositoryImpl
    ): SatelliteRepository
}