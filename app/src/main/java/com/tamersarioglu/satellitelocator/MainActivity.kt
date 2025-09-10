package com.tamersarioglu.satellitelocator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SatelliteNavHost(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}