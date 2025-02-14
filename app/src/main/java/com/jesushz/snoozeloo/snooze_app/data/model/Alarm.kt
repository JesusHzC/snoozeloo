package com.jesushz.snoozeloo.snooze_app.data.model

import java.util.UUID

data class Alarm(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val hour: Int,
    val minute: Int,
    val enabled: Boolean,
    val repeatDays: Set<DayValue>,
    val volume: Int,
    val ringtoneUri: String,
    val vibrate: Boolean
) {
    val isMorning: Boolean get() = hour in 0..11

    /**
     * Get the hour in 12-hour format. 13 = 1 PM
     */
    val hourTwelve: Int get() = if(hour > 12) {
        hour - 12
    } else {
        hour
    }

    val isOneTime: Boolean get() = repeatDays.isEmpty()
}
