package com.tamersarioglu.satellitelocator.di

import android.content.Context
import androidx.room.Room
import com.tamersarioglu.satellitelocator.data.local.database.SatelliteDatabase
import com.tamersarioglu.satellitelocator.data.local.dao.SatelliteDao
import com.tamersarioglu.satellitelocator.data.local.dao.SatelliteDetailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SatelliteDatabase {
        return Room.databaseBuilder(
            context,
            SatelliteDatabase::class.java,
            "satellite_database"
        ).build()
    }

    @Provides
    fun provideSatelliteDao(database: SatelliteDatabase): SatelliteDao {
        return database.satelliteDao()
    }

    @Provides
    fun provideSatelliteDetailDao(database: SatelliteDatabase): SatelliteDetailDao {
        return database.satelliteDetailDao()
    }
}