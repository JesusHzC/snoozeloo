@file:OptIn(ExperimentalFoundationApi::class)

package com.jesushz.snoozeloo.snooze_app.presentation.my_alarms.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jesushz.snoozeloo.R
import com.jesushz.snoozeloo.snooze_app.data.model.Alarm
import com.jesushz.snoozeloo.snooze_app.data.model.AlarmUi
import com.jesushz.snoozeloo.snooze_app.data.model.DayValue
import com.jesushz.snoozeloo.snooze_app.util.getDummyAlarm

@Composable
fun ListAlarms(
    modifier: Modifier = Modifier,
    alarms: List<AlarmUi> = emptyList(),
    onAlarmClick: (Alarm) -> Unit,
    onDeleteAlarmClick: (Alarm) -> Unit,
    onToggleAlarm: (Alarm) -> Unit,
    onToggleDayOfAlarm: (Alarm, DayValue) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = alarms,
            key = { it.alarm.id }
        ) { alarmUi ->
            val dismissState = rememberSwipeToDismissBoxState(
                confirmValueChange = { value ->
                    if (value == SwipeToDismissBoxValue.EndToStart) {
                        onDeleteAlarmClick(alarmUi.alarm)
                        true
                    } else false
                }
            )
            SwipeToDismissBox(
                state = dismissState,
                backgroundContent = {
                    if (dismissState.dismissDirection.name == SwipeToDismissBoxValue.EndToStart.name) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(
                                    RoundedCornerShape(10)
                                )
                                .background(
                                    color = MaterialTheme.colorScheme.error
                                ),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(id = R.string.delete_alarm),
                                modifier = Modifier
                                    .offset(
                                        x = (-20).dp
                                    ),
                                tint = MaterialTheme.colorScheme.onError
                            )
                        }
                    }
                },
                enableDismissFromStartToEnd = false,
            ) {
                AlarmCard(
                    alarmUi = alarmUi,
                    onAlarmClick = {
                        onAlarmClick(alarmUi.alarm)
                    },
                    onDeleteAlarmClick = {
                        onDeleteAlarmClick(alarmUi.alarm)
                    },
                    onToggleAlarm = {
                        onToggleAlarm(alarmUi.alarm)
                    },
                    onToggleDayOfAlarm = { day ->
                        onToggleDayOfAlarm(alarmUi.alarm, day)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListAlarmsPreview() {
    MaterialTheme {
        ListAlarms(
            alarms = listOf(
                AlarmUi(
                    alarm = getDummyAlarm(),
                    timeLeftInSeconds = 3600,
                    timeToSleepInSeconds = 3600
                )
            ),
            onAlarmClick = {},
            onDeleteAlarmClick = {},
            onToggleAlarm = {},
            onToggleDayOfAlarm = {_, _ ->}
        )
    }
}
