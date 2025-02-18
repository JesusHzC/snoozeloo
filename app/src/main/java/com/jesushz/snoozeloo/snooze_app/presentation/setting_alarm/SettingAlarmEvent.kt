package com.jesushz.snoozeloo.snooze_app.presentation.setting_alarm

import com.jesushz.snoozeloo.core.data.model.Ringtone

sealed interface SettingAlarmEvent {

    data object OnSuccess: SettingAlarmEvent
    data class OnSelectedRingtone(val ringtone: Ringtone): SettingAlarmEvent

}
