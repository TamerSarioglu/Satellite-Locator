package com.tamersarioglu.satellitelocator.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.tamersarioglu.satellitelocator.presentation.ui.list.SatelliteListScreen

@Composable
fun SatelliteNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = SatelliteDestination.SatelliteList,
        modifier = modifier
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

@Composable
fun SatelliteDetailScreen(
    satelliteId: Int,
    onBackClick: () -> Unit
) {
    // TODO: Implement satellite detail screen
}