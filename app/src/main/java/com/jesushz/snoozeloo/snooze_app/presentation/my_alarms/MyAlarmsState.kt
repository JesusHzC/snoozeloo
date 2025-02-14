package com.jesushz.snoozeloo.snooze_app.presentation.my_alarms

import com.jesushz.snoozeloo.snooze_app.data.model.Alarm

data class MyAlarmsState(
    val alarms: List<Alarm> = emptyList(),
)
