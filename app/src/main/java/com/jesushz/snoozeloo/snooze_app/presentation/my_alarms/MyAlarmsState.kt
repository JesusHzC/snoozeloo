package com.jesushz.snoozeloo.snooze_app.presentation.my_alarms

data class MyAlarmsState(
    val alarms: List<String> = (1..20).map { it.toString() },
)
