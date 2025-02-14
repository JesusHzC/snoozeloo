package com.jesushz.snoozeloo.snooze_app.domain.use_case

import com.jesushz.snoozeloo.snooze_app.data.model.DayValue
import java.time.LocalDateTime

interface GetFutureDateUseCase {

    operator fun invoke(
        hour: Int,
        minute: Int,
        repeatDays: Set<DayValue> = emptySet(),
        curDateTime: LocalDateTime = LocalDateTime.now()
    ): LocalDateTime

}
