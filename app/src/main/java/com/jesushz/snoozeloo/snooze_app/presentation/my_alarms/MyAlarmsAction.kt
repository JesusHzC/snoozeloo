package com.jesushz.snoozeloo.snooze_app.presentation.my_alarms

import com.jesushz.snoozeloo.snooze_app.data.model.Alarm
import com.jesushz.snoozeloo.snooze_app.data.model.DayValue

sealed interface MyAlarmsAction {

    data object OnCreateNewAlarm: MyAlarmsAction
    data class OnAlarmSelected(val alarm: Alarm): MyAlarmsAction
    data class OnDeleteAlarm(val alarm: Alarm): MyAlarmsAction
    data class OnToggleAlarm(val alarm: Alarm): MyAlarmsAction
    data class OnToggleDayOfAlarm(val alarm: Alarm, val day: DayValue): MyAlarmsAction

}
