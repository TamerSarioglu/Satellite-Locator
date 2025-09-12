package com.tamersarioglu.satellitelocator.presentation.navigation

import android.app.Activity
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun BackPressControlOnListScreen(navController: NavHostController) {
    val context = LocalContext.current
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var lastBackPressed by remember { mutableLongStateOf(0L) }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val isAtRootDestination = backStackEntry?.destination?.route?.contains("SatelliteList") == true

    DisposableEffect(isAtRootDestination) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isAtRootDestination) {
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastBackPressed < 5000) {
                        (context as? Activity)?.finish()
                    } else {
                        lastBackPressed = currentTime
                        Toast.makeText(
                            context,
                            "Çıkmak için tekrar basınız",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    navController.popBackStack()
                }
            }
        }

        backPressedDispatcher?.addCallback(callback)

        onDispose {
            callback.remove()
        }
    }
}