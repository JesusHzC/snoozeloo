package com.jesushz.snoozeloo.snooze_app.presentation.my_alarms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesushz.snoozeloo.R
import com.jesushz.snoozeloo.snooze_app.presentation.my_alarms.components.EmptyAlarms
import com.jesushz.snoozeloo.snooze_app.presentation.my_alarms.components.FloatingButton
import com.jesushz.snoozeloo.snooze_app.presentation.my_alarms.components.ListAlarms
import com.jesushz.snoozeloo.core.presentation.theme.MontserratFamily
import com.jesushz.snoozeloo.snooze_app.data.model.Alarm
import com.jesushz.snoozeloo.snooze_app.data.model.AlarmUi
import com.jesushz.snoozeloo.snooze_app.util.getDummyAlarm
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyAlarmsScreenRoot(
    viewModel: MyAlarmsViewModel = koinViewModel(),
    onCreateNewAlarm: () -> Unit,
    onAlarmSelected: (Alarm) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MyAlarmsScreen(
        state = state,
        onAction = { action ->
            when (action) {
                MyAlarmsAction.OnCreateNewAlarm -> {
                    onCreateNewAlarm()
                }
                is MyAlarmsAction.OnAlarmSelected -> {
                    onAlarmSelected(action.alarm)
                }
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun MyAlarmsScreen(
    state: MyAlarmsState,
    onAction: (MyAlarmsAction) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingButton(
                onButtonClick = {
                    onAction(
                        MyAlarmsAction.OnCreateNewAlarm
                    )
                }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.your_alarms),
                fontFamily = MontserratFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
            when {
                state.alarms.isEmpty() -> {
                    EmptyAlarms(
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
                else -> {
                    ListAlarms(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 64.dp),
                        alarms = state.alarms,
                        onAlarmClick = {
                            onAction(
                                MyAlarmsAction.OnAlarmSelected(it)
                            )
                        },
                        onDeleteAlarmClick = {
                            onAction(
                                MyAlarmsAction.OnDeleteAlarm(it)
                            )
                        },
                        onToggleAlarm = {
                            onAction(
                                MyAlarmsAction.OnToggleAlarm(it)
                            )
                        },
                        onToggleDayOfAlarm = { alarm, day ->
                            onAction(
                                MyAlarmsAction.OnToggleDayOfAlarm(alarm, day)
                            )
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MyAlarmsScreenPreview() {
    MaterialTheme {
        MyAlarmsScreen(
            state = MyAlarmsState(
                alarms = listOf(
                    AlarmUi(
                        alarm = getDummyAlarm(),
                        timeLeftInSeconds = 3600,
                        timeToSleepInSeconds = 3600
                    )
                )
            ),
            onAction = {},
        )
    }
}
