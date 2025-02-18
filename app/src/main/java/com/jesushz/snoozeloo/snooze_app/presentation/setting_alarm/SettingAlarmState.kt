package com.jesushz.snoozeloo.snooze_app.presentation.setting_alarm

import com.jesushz.snoozeloo.core.data.model.Ringtone
import com.jesushz.snoozeloo.snooze_app.data.model.Alarm
import com.jesushz.snoozeloo.snooze_app.data.model.DayValue

data class SettingAlarmState(
    val hour: String = "",
    val minutes: String = "",
    val name: String = "",
    val repeatDays: List<DayValue> = emptyList(),
    val alarmRingtone: Ringtone? = null,
    val alarmVolume: Float = 50f,
    val alarmVibrate: Boolean = true,
    val isSaveButtonEnabled: Boolean = false,
    val timeLeftInSeconds: Long? = null,
    val alarmSelected: Alarm? = null
)
