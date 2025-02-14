package com.jesushz.snoozeloo.snooze_app.data.mappers

import com.jesushz.snoozeloo.core.data.database.entity.AlarmEntity
import com.jesushz.snoozeloo.snooze_app.data.model.Alarm

fun Alarm.toAlarmEntity(): AlarmEntity {
    return AlarmEntity(
        id = id,
        name = name,
        hour = hour,
        minute = minute,
        enabled = enabled,
        days = repeatDays.toSetOfInt(),
        volume = volume,
        audioUri = ringtoneUri,
        vibrate = vibrate
    )
}

fun AlarmEntity.toAlarm(): Alarm {
    return Alarm(
        id = id,
        name = name,
        hour = hour,
        minute = minute,
        enabled = enabled,
        repeatDays = days.toDayValues(),
        volume = volume,
        ringtoneUri = audioUri,
        vibrate = vibrate
    )
}
