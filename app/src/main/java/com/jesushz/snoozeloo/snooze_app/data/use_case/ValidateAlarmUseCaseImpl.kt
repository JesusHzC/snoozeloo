package com.jesushz.snoozeloo.snooze_app.data.use_case

import com.jesushz.snoozeloo.snooze_app.domain.use_case.ValidateAlarmUseCase

class ValidateAlarmUseCaseImpl: ValidateAlarmUseCase {

    override fun invoke(hour: String, minutes: String): Boolean {
        val hourInt = hour.toIntOrNull() ?: return false
        val minuteInt = minutes.toIntOrNull() ?: return false

        return hourInt in 0..23 &&
                minuteInt in 0..59
    }

}
