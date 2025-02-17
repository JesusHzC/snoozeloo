package com.jesushz.snoozeloo.snooze_app.presentation.setting_alarm

sealed interface SettingAlarmEvent {

    data object OnSuccess: SettingAlarmEvent

}
