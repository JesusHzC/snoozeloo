package com.jesushz.snoozeloo.snooze_app.data.model

data class AlarmUi(
    val alarm: Alarm,
    val timeLeftInSeconds: Long,
    val timeToSleepInSeconds: Long?
)
