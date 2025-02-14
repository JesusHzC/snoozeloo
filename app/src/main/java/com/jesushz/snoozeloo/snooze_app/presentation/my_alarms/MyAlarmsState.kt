package com.jesushz.snoozeloo.snooze_app.presentation.my_alarms

import com.jesushz.snoozeloo.snooze_app.data.model.AlarmUi

data class MyAlarmsState(
    val alarms: List<AlarmUi> = emptyList(),
)
