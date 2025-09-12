package com.tamersarioglu.satellitelocator.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.tamersarioglu.satellitelocator.presentation.ui.detail.SatelliteDetailScreen
import com.tamersarioglu.satellitelocator.presentation.ui.list.SatelliteListScreen

@Composable
fun SatelliteNavHost(
    navController: NavHostController = rememberNavController()
) {
    BackPressControlOnListScreen(navController)
    NavHost(
        navController = navController,
        startDestination = SatelliteDestination.SatelliteList,
    ) {
        composable<SatelliteDestination.SatelliteList> {
            SatelliteListScreen(
                onSatelliteClick = { satelliteId ->
                    navController.navigate(
                        SatelliteDestination.SatelliteDetail(satelliteId)
                    )
                }
            )
        }

        composable<SatelliteDestination.SatelliteDetail> { backStackEntry ->
            val destination = backStackEntry.toRoute<SatelliteDestination.SatelliteDetail>()
            SatelliteDetailScreen(
                satelliteId = destination.satelliteId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}