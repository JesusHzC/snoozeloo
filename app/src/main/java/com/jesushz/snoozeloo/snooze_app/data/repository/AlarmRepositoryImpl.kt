package com.jesushz.snoozeloo.snooze_app.data.repository

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import com.jesushz.snoozeloo.core.data.database.dao.AlarmDao
import com.jesushz.snoozeloo.core.domain.audio.AudioManager
import com.jesushz.snoozeloo.core.util.hideNotification
import com.jesushz.snoozeloo.snooze_app.data.mappers.toAlarm
import com.jesushz.snoozeloo.snooze_app.data.mappers.toAlarmEntity
import com.jesushz.snoozeloo.snooze_app.data.model.Alarm
import com.jesushz.snoozeloo.snooze_app.data.model.DayValue
import com.jesushz.snoozeloo.snooze_app.domain.repository.AlarmRepository
import com.jesushz.snoozeloo.snooze_app.domain.scheduler.AlarmScheduler
import com.jesushz.snoozeloo.snooze_app.util.AlarmConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AlarmRepositoryImpl(
    private val alarmDao: AlarmDao,
    private val context: Context,
    private val alarmScheduler: AlarmScheduler,
    private val audioManager: AudioManager
): AlarmRepository {

    private val vibrator: Vibrator = context.getSystemService(Vibrator::class.java)

    override fun getAllAlarms(): Flow<List<Alarm>> {
        return alarmDao.getAll().map { alarms ->
            alarms.map { it.toAlarm() }
        }
    }

    override suspend fun getAlarmById(id: String): Alarm? {
        return alarmDao.getAlarmById(id)?.toAlarm()
    }

    override suspend fun upsertAlarm(alarm: Alarm) {
        alarmDao.upsertAlarm(alarm.toAlarmEntity())
        if (alarm.enabled) {
            alarmScheduler.schedule(alarm)
        }
    }

    override suspend fun toggle(alarm: Alarm) {
        val isEnabled = !alarm.enabled
        alarmDao.upsertAlarm(alarm.copy(enabled = isEnabled).toAlarmEntity())
    }

    override suspend fun toggleDay(day: DayValue, alarm: Alarm) {
        // Cancel alarm first to avoid conflict.
        alarmScheduler.cancel(alarm)


        val repeatDays = alarm.repeatDays.toMutableSet()

        // Remove it from the set if it exists.
        if (repeatDays.contains(day)) {
            repeatDays.remove(day)
        } else { // Or else, add it in the set
            repeatDays.add(day)
        }

        val updatedAlarm = alarm.copy(repeatDays = repeatDays)
        upsertAlarm(updatedAlarm)
    }

    override suspend fun disableAlarmById(id: String) {
        alarmDao.disableAlarmById(id)
    }

    override suspend fun deleteAlarmById(id: String) {
        getAlarmById(id)?.let {
            alarmScheduler.cancel(it)
        }

        alarmDao.deleteAlarmById(id)
    }

    override suspend fun scheduleAllEnabledAlarms() {
        getAllAlarms().first().map { alarm ->
            if (alarm.enabled) {
                alarmScheduler.schedule(alarm)
            }
        }
    }

    override fun setupEffects(alarm: Alarm) {
        val pattern = AlarmConstants.VIBRATE_PATTERN_LONG_ARR
        if (alarm.vibrate) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, 0))
        }

        val volume = (alarm.volume / 100f)
        if (!audioManager.isPlaying()) {
            audioManager.play(uri = alarm.ringtoneUri, isLooping = true, volume = volume)
        }
    }

    override fun stopEffectsAndHideNotification(alarm: Alarm) {
        context.hideNotification(alarm.id.hashCode())
        audioManager.stop()
        vibrator.cancel()
    }

    override fun snoozeAlarm(alarm: Alarm) {
        alarmScheduler.schedule(
            alarm = alarm,
            shouldSnooze = true
        )
    }

}
