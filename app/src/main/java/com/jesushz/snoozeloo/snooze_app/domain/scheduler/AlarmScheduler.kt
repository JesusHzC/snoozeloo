package com.jesushz.snoozeloo.snooze_app.domain.scheduler

import com.jesushz.snoozeloo.snooze_app.data.model.Alarm

interface AlarmScheduler {

    fun schedule(alarm: Alarm, shouldSnooze: Boolean = false)

    fun cancel(alarm: Alarm)

}
