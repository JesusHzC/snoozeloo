package com.jesushz.snoozeloo.snooze_app.alarm_receiver

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.core.app.NotificationCompat
import com.jesushz.snoozeloo.R
import com.jesushz.snoozeloo.core.domain.audio.AudioManager
import com.jesushz.snoozeloo.core.domain.audio.AudioManager.Companion.ALARM_MAX_REMINDER_MILLIS
import com.jesushz.snoozeloo.core.util.isScreenOn
import com.jesushz.snoozeloo.snooze_app.data.model.Alarm
import com.jesushz.snoozeloo.snooze_app.domain.repository.AlarmRepository
import com.jesushz.snoozeloo.snooze_app.presentation.reminder.ReminderActivity
import com.jesushz.snoozeloo.snooze_app.util.AlarmConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.UUID

class AlarmReceiver: BroadcastReceiver(), KoinComponent {

    private val alarmRepository: AlarmRepository by inject()
    private val audioManager: AudioManager by inject()
    private val scope: CoroutineScope by inject()

    override fun onReceive(context: Context?, intent: Intent?) {
        scope.launch {
            val alarmId = intent?.getStringExtra(AlarmConstants.EXTRA_ALARM_ID) ?: return@launch
            if (context == null) return@launch

            val alarm = alarmRepository.getAlarmById(alarmId) ?: return@launch
            val alarmName = alarm.name.ifEmpty { "Alarm" }

            val reminderIntent = Intent(context, ReminderActivity::class.java).apply {
                putExtra(AlarmConstants.EXTRA_ALARM_ID, alarm.id)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }

            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                reminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            if (context.isScreenOn()) {
                showAlarmNotification(context, pendingIntent, alarm)

                scope.launch {
                    delay(ALARM_MAX_REMINDER_MILLIS)
                    val dismissAlarmIntent =
                        Intent(context, DismissAlarmReceiver::class.java).apply {
                            putExtra(AlarmConstants.EXTRA_ALARM_ID, alarm.id)
                            putExtra(AlarmConstants.EXTRA_ALARM_CUSTOM_CHANNEL_ID, alarm.id)
                            putExtra(AlarmConstants.EXTRA_SHOULD_SNOOZE, true)
                        }
                    context.sendBroadcast(dismissAlarmIntent)
                }
            } else {
                val channel = NotificationChannel(alarmId, alarmName, NotificationManager.IMPORTANCE_HIGH)
                val notificationManager: NotificationManager =
                    context.getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(channel)
                val builder = NotificationCompat.Builder(context, alarmId)
                    .setSmallIcon(R.drawable.ic_alarm)
                    .setContentTitle(alarmName)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)
                    .setFullScreenIntent(pendingIntent, true)
                    .build()

                notificationManager.notify(alarm.id.hashCode(), builder)
            }
        }
    }

    private fun showAlarmNotification(
        context: Context,
        pendingIntent: PendingIntent,
        alarm: Alarm
    ) {
        val notification = buildAlarmNotification(context, pendingIntent, alarm)
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.notify(alarm.id.hashCode(), notification)
    }

    private fun buildAlarmNotification(
        context: Context,
        pendingIntent: PendingIntent,
        alarm: Alarm
    ): Notification {
        val ringtoneUri: Uri = alarm.ringtoneUri.let {
            if (it.isNotBlank()) {
                return@let Uri.parse(it)
            }

            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        }
        val channelId = alarm.id
        val alarmName = alarm.name.ifBlank { "Alarm" }

        audioManager.play(
            uri = ringtoneUri.toString(),
            isLooping = true,
            volume = alarm.volume / 100f
        )
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        val channel = NotificationChannel(
            channelId,
            alarmName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setBypassDnd(true)
            enableVibration(alarm.vibrate)
        }
        notificationManager.createNotificationChannel(channel)

        val dismissAlarmPendingIntent = getDismissAlarmPendingIntent(context, alarm, channelId)
        val vibrateArray = AlarmConstants.VIBRATE_PATTERN_LONG_ARR

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_alarm)
            .setContentTitle(alarmName)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setSilent(true)
            .setChannelId(channelId)
            .addAction(-1, "Snooze", getDismissAlarmPendingIntent(context, alarm, channelId, true))
            .addAction(-1, "Turn off", dismissAlarmPendingIntent)
            .setDeleteIntent(dismissAlarmPendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setCategory(NotificationCompat.CATEGORY_ALARM)

        if (alarm.vibrate) {
            builder.setVibrate(vibrateArray) // Ignored in Android O above since we set the vibration in NotificationChannel.
        } else {
            builder.setVibrate(LongArray(1) { 0L })
        }

        return builder.build().apply {
            flags = flags or Notification.FLAG_INSISTENT
        }
    }

    private fun getDismissAlarmPendingIntent(
        context: Context,
        alarm: Alarm,
        channelId: String,
        shouldSnooze: Boolean = false
    ): PendingIntent {
        val intent = Intent(context, DismissAlarmReceiver::class.java).apply {
            putExtra(AlarmConstants.EXTRA_ALARM_ID, alarm.id)
            putExtra(AlarmConstants.EXTRA_ALARM_CUSTOM_CHANNEL_ID, channelId)
            putExtra(AlarmConstants.EXTRA_SHOULD_SNOOZE, shouldSnooze)
        }
        val requestCode = UUID.randomUUID().toString().hashCode()
        return PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

}
