package com.jesushz.snoozeloo.snooze_app.domain.use_case

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface GetTimeToSleepInSecondsUseCase {

    operator fun invoke(
        hour: Int,
        futureDateTime: LocalDateTime,
        curDateTime: LocalDateTime = LocalDateTime.now()
    ): Flow<Long?>

}
