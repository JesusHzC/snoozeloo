package com.jesushz.snoozeloo.snooze_app.data.use_case

import com.jesushz.snoozeloo.snooze_app.domain.use_case.GetTimeLeftInSecondsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.time.Duration.Companion.minutes

class GetTimeLeftInSecondsUseCaseImpl: GetTimeLeftInSecondsUseCase {

    override fun invoke(futureDateTime: LocalDateTime): Flow<Long> {
        return flow {
            while (true) {
                val curDateTime = LocalDateTime.now()
                val seconds = ChronoUnit.SECONDS.between(curDateTime, futureDateTime)
                emit(seconds)

                delay(1.minutes)
            }
        }
    }

}
