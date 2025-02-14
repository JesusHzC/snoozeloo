package com.jesushz.snoozeloo.snooze_app.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jesushz.snoozeloo.core.presentation.components.Route
import com.jesushz.snoozeloo.snooze_app.presentation.audio.AudioScreenRoot
import com.jesushz.snoozeloo.snooze_app.presentation.my_alarms.MyAlarmsScreenRoot
import com.jesushz.snoozeloo.snooze_app.presentation.setting_alarm.SettingAlarmScreenRoot

@Composable
fun NavigationRoot(
    navController: NavController,
    startDestination: Route
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = startDestination
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