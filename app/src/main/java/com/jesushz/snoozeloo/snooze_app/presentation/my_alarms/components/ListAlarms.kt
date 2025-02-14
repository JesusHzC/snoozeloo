package com.jesushz.snoozeloo.snooze_app.presentation.my_alarms.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            key = { it }
        ) { alarmUi ->
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
