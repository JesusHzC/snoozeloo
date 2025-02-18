package com.jesushz.snoozeloo.snooze_app.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.jesushz.snoozeloo.core.presentation.components.Route
import com.jesushz.snoozeloo.core.presentation.util.sharedKoinViewModel
import com.jesushz.snoozeloo.snooze_app.presentation.audio.AudioScreenRoot
import com.jesushz.snoozeloo.snooze_app.presentation.my_alarms.MyAlarmsScreenRoot
import com.jesushz.snoozeloo.snooze_app.presentation.setting_alarm.SettingAlarmAction
import com.jesushz.snoozeloo.snooze_app.presentation.setting_alarm.SettingAlarmScreenRoot
import com.jesushz.snoozeloo.snooze_app.presentation.setting_alarm.SettingAlarmViewModel
import kotlinx.coroutines.flow.update
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

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
            composable<Route.MyAlarms> { entry ->
                val selectedAlarmViewModel =
                    entry.sharedKoinViewModel<SelectedAlarmViewModel>(navController)

                LaunchedEffect(true) {
                    selectedAlarmViewModel.onSelectedAlarm(null)
                }

                MyAlarmsScreenRoot(
                    onCreateNewAlarm = {
                        navController.navigate(Route.SettingAlarm)
                    },
                    onAlarmSelected = { alarm ->
                        selectedAlarmViewModel.onSelectedAlarm(alarm)
                        navController.navigate(Route.SettingAlarm)
                    }
                )
            }

            composable<Route.SettingAlarm> { entry ->
                val selectedAlarmViewModel =
                    entry.sharedKoinViewModel<SelectedAlarmViewModel>(navController)
                val selectedRingtoneViewModel =
                    entry.sharedKoinViewModel<SelectedRingtoneViewModel>(navController)
                val selectedAlarm by selectedAlarmViewModel.selectedAlarm.collectAsStateWithLifecycle()
                val selectedRingtone by selectedRingtoneViewModel.selectedRingtone.collectAsStateWithLifecycle()
                val viewModel: SettingAlarmViewModel = koinViewModel()

                LaunchedEffect(selectedAlarm) {
                    selectedAlarm?.let {
                        viewModel.onAction(SettingAlarmAction.OnAlarmSelected(it))
                    }
                }

                LaunchedEffect(selectedRingtone) {
                    selectedRingtone?.let {
                        viewModel.onAction(SettingAlarmAction.OnAlarmRingtoneSelected(it))
                    }
                }

                SettingAlarmScreenRoot(
                    viewModel = viewModel,
                    onSelectedRingtone = {
                        selectedRingtoneViewModel.onSelectedRingtone(it)
                    },
                    onNavigateBack = {
                        navController.navigateUp()
                    },
                    onNavigateToAudio = {
                        navController.navigate(Route.Audio)
                    }
                )
            }

            composable<Route.Audio> { entry ->
                val selectedRingtoneViewModel =
                    entry.sharedKoinViewModel<SelectedRingtoneViewModel>(navController)

                val selectedRingtone by selectedRingtoneViewModel.selectedRingtone.collectAsStateWithLifecycle()

                AudioScreenRoot(
                    audioSelected = selectedRingtone,
                    onAudioSelected = {
                        selectedRingtoneViewModel.onSelectedRingtone(it)
                    },
                    onNavigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}