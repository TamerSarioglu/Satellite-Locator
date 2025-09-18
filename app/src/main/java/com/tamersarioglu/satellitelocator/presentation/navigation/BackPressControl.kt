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
import com.tamersarioglu.satellitelocator.utils.AppConfig

@Composable
fun BackPressHandler(
    navController: NavHostController,
    enableDoubleBackToExit: Boolean = true,
    rootDestinationRoute: String = AppConfig.ROOT_DESTINATION_ROUTE,
    exitMessage: String = AppConfig.DEFAULT_EXIT_MESSAGE,
    doubleBackPressInterval: Long = AppConfig.DOUBLE_BACK_PRESS_INTERVAL_MS,
    onBackPressed: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    var lastBackPressed by remember { mutableLongStateOf(0L) }

    val backStackEntry by navController.currentBackStackEntryAsState()
    val isAtRootDestination = backStackEntry?.destination?.route?.contains(rootDestinationRoute) == true

    DisposableEffect(isAtRootDestination, enableDoubleBackToExit) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when {
                    onBackPressed != null -> onBackPressed()
                    isAtRootDestination && enableDoubleBackToExit -> {
                        handleDoubleBackPressToExit(context, lastBackPressed, doubleBackPressInterval, exitMessage) {
                            lastBackPressed = it
                        }
                    }
                    !isAtRootDestination -> {
                        navController.popBackStack()
                    }
                    else -> {
                        (context as? Activity)?.finish()
                    }
                }
            }
        }

        backPressedDispatcher?.addCallback(callback)

        onDispose {
            callback.remove()
        }
    }
}

private fun handleDoubleBackPressToExit(
    context: android.content.Context,
    lastBackPressed: Long,
    interval: Long,
    message: String,
    updateLastBackPressed: (Long) -> Unit
) {
    val currentTime = System.currentTimeMillis()
    if (currentTime - lastBackPressed < interval) {
        (context as? Activity)?.finish()
    } else {
        updateLastBackPressed(currentTime)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun BackPressControlOnListScreen(navController: NavHostController) {
    BackPressHandler(navController)
}