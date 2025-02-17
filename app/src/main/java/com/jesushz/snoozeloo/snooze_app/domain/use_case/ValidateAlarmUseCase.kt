package com.jesushz.snoozeloo.snooze_app.domain.use_case

interface ValidateAlarmUseCase {

    operator fun invoke(
        hour: String,
        minutes: String
    ): Boolean

}
