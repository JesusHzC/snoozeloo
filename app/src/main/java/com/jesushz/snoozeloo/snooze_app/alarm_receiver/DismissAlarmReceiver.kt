package com.jesushz.snoozeloo.snooze_app.alarm_receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.jesushz.snoozeloo.core.domain.audio.AudioManager
import com.jesushz.snoozeloo.core.util.hideNotification
import com.jesushz.snoozeloo.snooze_app.domain.repository.AlarmRepository
import com.jesushz.snoozeloo.snooze_app.domain.scheduler.AlarmScheduler
import com.jesushz.snoozeloo.snooze_app.util.AlarmConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DismissAlarmReceiver: BroadcastReceiver(), KoinComponent {

    private val alarmRepository: AlarmRepository by inject()
    private val alarmScheduler: AlarmScheduler by inject()
    private val audioManager: AudioManager by inject()
    private val scope: CoroutineScope by inject()

    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmId = intent?.getStringExtra(AlarmConstants.EXTRA_ALARM_ID) ?: return
        val shouldSnooze = intent.getBooleanExtra(AlarmConstants.EXTRA_SHOULD_SNOOZE, false)
        if (context == null) return

        audioManager.stop()
        context.hideNotification(alarmId.hashCode())
        intent.getStringExtra(AlarmConstants.EXTRA_ALARM_CUSTOM_CHANNEL_ID)?.let { channelId ->
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.deleteNotificationChannel(channelId)
        }

        scope.launch {
            withContext(Dispatchers.Main) {
                val alarm = alarmRepository.getAlarmById(alarmId) ?: return@withContext

                if (shouldSnooze) {
                    alarmScheduler.schedule(
                        alarm = alarm,
                        shouldSnooze = true
                    )
                    return@withContext
                }

                if (alarm.isOneTime) {
                    alarmRepository.disableAlarmById(alarmId)
                } else {
                    alarmRepository.disableAlarmById(alarmId)
                    alarmRepository.upsertAlarm(alarm.copy(enabled = true))
                }
            }
        }
    }

}
