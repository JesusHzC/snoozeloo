package com.jesushz.snoozeloo.core.util

import java.util.Locale

fun formatHourMinute(hour: Int, minute: Int): String {
    return String.format(Locale.getDefault(), "%02d:%02d", hour, minute)
}
