package com.jesushz.snoozeloo.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.jesushz.snoozeloo.presentation.components.Route
import com.jesushz.snoozeloo.presentation.my_alarms.MyAlarmsScreenRoot
import com.jesushz.snoozeloo.presentation.theme.SnoozelooTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var showSplashScreen = true
        lifecycleScope.launch {
            delay(1000L)
            showSplashScreen = false
        }
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                showSplashScreen
            }
        }
        enableEdgeToEdge()
        setContent {
            SnoozelooTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.SnoozeGraph
                ) {
                    navigation<Route.SnoozeGraph>(
                        startDestination = Route.MyAlarms
                    ) {
                        composable<Route.MyAlarms> {
                            MyAlarmsScreenRoot()
                        }
                    }
                }
            }
        }
    }
}
