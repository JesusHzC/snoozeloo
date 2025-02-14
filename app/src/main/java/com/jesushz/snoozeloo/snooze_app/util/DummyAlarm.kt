package com.jesushz.snoozeloo.snooze_app.util

import com.jesushz.snoozeloo.snooze_app.data.model.Alarm

fun getDummyAlarm(
    name: String = "Work",
    hour: Int = 10,
    minute: Int = 0,
    enabled: Boolean = true
) = Alarm(
    name = name,
    hour = hour,
    minute = minute,
    enabled = enabled,
    repeatDays = setOf(),
    volume = 70,
    ringtoneUri = "",
    vibrate = true
)
