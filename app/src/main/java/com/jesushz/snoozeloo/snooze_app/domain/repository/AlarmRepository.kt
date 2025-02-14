package com.jesushz.snoozeloo.snooze_app.domain.repository

import com.jesushz.snoozeloo.snooze_app.data.model.Alarm
import com.jesushz.snoozeloo.snooze_app.data.model.DayValue
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    /**
     * Mange alarm on database
     */
    fun getAllAlarms(): Flow<List<Alarm>>

    suspend fun getAlarmById(id: String): Alarm?

    suspend fun upsertAlarm(alarm: Alarm)

    /**
     * We can enable or disable an alarm. It should be scheduled or cancelled from AlarmManager
     */
    suspend fun toggle(alarm: Alarm)

    suspend fun toggleDay(day: DayValue, alarm: Alarm)

    /**
     * Disable the alarm after it has been turned off by the user. No need to cancel it from AlarmManager
     */
    suspend fun disableAlarmById(id: String)

    suspend fun deleteAlarmById(id: String)

    suspend fun scheduleAllEnabledAlarms()

    /**
     * Play vibration and ringtone
     */
    fun setupEffects(alarm: Alarm)

    /**
     * Stop vibration and ringtone
     */
    fun stopEffectsAndHideNotification(alarm: Alarm)

    fun snoozeAlarm(alarm: Alarm)

}
