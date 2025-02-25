package com.jesushz.snoozeloo.snooze_app.alarm_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.jesushz.snoozeloo.snooze_app.domain.repository.AlarmRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BootCompletedReceiver: BroadcastReceiver(), KoinComponent {

    private val alarmRepository: AlarmRepository by inject()
    private val scope: CoroutineScope by inject()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            scope.launch {
                alarmRepository.scheduleAllEnabledAlarms()
            }
        }
    }

}
