package com.jesushz.snoozeloo.snooze_app.presentation.setting_alarm

import com.jesushz.snoozeloo.core.data.model.Ringtone
import com.jesushz.snoozeloo.snooze_app.data.model.DayValue

sealed interface SettingAlarmAction {

    data class OnHourChange(val hour: String) : SettingAlarmAction
    data class OnMinutesChange(val minutes: String) : SettingAlarmAction
    data class OnNameChange(val name: String) : SettingAlarmAction
    data class OnRepeatDaySelected(val day: DayValue) : SettingAlarmAction
    data class OnAlarmRingtoneSelected(val ringtone: Ringtone) : SettingAlarmAction
    data class OnAlarmVolumeChange(val volume: Float) : SettingAlarmAction
    data class OnAlarmVibrate(val vibrate: Boolean) : SettingAlarmAction
    data object OnBackClick : SettingAlarmAction
    data object OnSaveClick : SettingAlarmAction
    data object OnNavigateToAudio : SettingAlarmAction

}
