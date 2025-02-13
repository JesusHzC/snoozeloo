package com.jesushz.snoozeloo

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
import com.jesushz.snoozeloo.snooze_app.presentation.audio.AudioScreenRoot
import com.jesushz.snoozeloo.core.presentation.components.Route
import com.jesushz.snoozeloo.snooze_app.presentation.my_alarms.MyAlarmsScreenRoot
import com.jesushz.snoozeloo.snooze_app.presentation.setting_alarm.SettingAlarmScreenRoot
import com.jesushz.snoozeloo.core.presentation.theme.SnoozelooTheme
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
                            MyAlarmsScreenRoot(
                                onNavigateToSettingAlarm = {
                                    navController.navigate(Route.SettingAlarm)
                                }
                            )
                        }

                        composable<Route.SettingAlarm> {
                            SettingAlarmScreenRoot(
                                navigateToAudioScreen = {
                                    navController.navigate(Route.Audio)
                                }
                            )
                        }

                        composable<Route.Audio> {
                            AudioScreenRoot()
                        }
                    }
                }
            }
        }
    }
}
