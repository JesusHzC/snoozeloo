package com.jesushz.snoozeloo.snooze_app.presentation.reminder

import com.jesushz.snoozeloo.snooze_app.data.model.Alarm

sealed interface ReminderEvent {

    data class OnAlarmFetched(val alarm: Alarm): ReminderEvent

    data object OnTimerExpired: ReminderEvent

    data object AlarmIsNotExisting: ReminderEvent

}
