package com.jesushz.snoozeloo.snooze_app.presentation.reminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesushz.snoozeloo.core.domain.audio.AudioManager.Companion.ALARM_MAX_REMINDER_MILLIS
import com.jesushz.snoozeloo.snooze_app.data.model.Alarm
import com.jesushz.snoozeloo.snooze_app.domain.repository.AlarmRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ReminderViewModel(
    private val alarmId: String,
    private val alarmRepository: AlarmRepository
): ViewModel() {

    private val eventChannel = Channel<ReminderEvent>()
    val events = eventChannel.receiveAsFlow()

    private lateinit var alarm: Alarm

    init {
        viewModelScope.launch {
            val alarm = getAlarmById() ?: run {
                eventChannel.send(ReminderEvent.AlarmIsNotExisting)
                return@launch
            }

            // Set the fetched alarm to instance variable
            this@ReminderViewModel.alarm = alarm

            // Send an event that we have an existing alarm.
            eventChannel.send(ReminderEvent.OnAlarmFetched(alarm))

            // Setup effects (vibrate & ringtone) AND start 5 minute timer.
            alarmRepository.setupEffects(alarm)
            startTimer()
        }
    }

    fun snoozeAlarm() {
        alarmRepository.stopEffectsAndHideNotification(alarm)
        alarmRepository.snoozeAlarm(alarm)
    }

    fun disableOrRescheduleAlarm() = viewModelScope.launch {
        if (alarm.isOneTime) {
            alarmRepository.disableAlarmById(alarmId)
        } else {
            alarmRepository.upsertAlarm(alarm.copy(enabled = true))
        }
        alarmRepository.stopEffectsAndHideNotification(alarm)
    }

    private fun startTimer() = viewModelScope.launch {
        delay(ALARM_MAX_REMINDER_MILLIS)
        eventChannel.send(ReminderEvent.OnTimerExpired)
        snoozeAlarm()
    }

    private suspend fun getAlarmById(): Alarm? {
        return alarmRepository.getAlarmById(alarmId)
    }

}
