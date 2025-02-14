package com.jesushz.snoozeloo.snooze_app.domain.use_case

import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface GetTimeLeftInSecondsUseCase {

    operator fun invoke(
        futureDateTime: LocalDateTime
    ): Flow<Long>

}
