package com.jesushz.snoozeloo.core.presentation.components

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object SnoozeGraph : Route

    @Serializable
    data object MyAlarms : Route

    @Serializable
    data object SettingAlarm : Route

    @Serializable
    data object Audio : Route

}
