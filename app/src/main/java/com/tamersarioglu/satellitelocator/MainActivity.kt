package com.tamersarioglu.satellitelocator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tamersarioglu.satellitelocator.presentation.navigation.SatelliteNavHost
import com.tamersarioglu.satellitelocator.ui.theme.SatelliteLocatorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SatelliteLocatorTheme {
                SatelliteNavHost()
            }
        }
    }
}