package com.jesushz.snoozeloo.snooze_app.data.use_case

import com.jesushz.snoozeloo.core.util.convertLocalDateTimeToEpochSeconds
import com.jesushz.snoozeloo.snooze_app.domain.use_case.GetTimeToSleepInSecondsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.time.Duration.Companion.minutes

class GetTimeToSleepInSecondsUseCaseImpl: GetTimeToSleepInSecondsUseCase {

    override fun invoke(
        hour: Int,
        futureDateTime: LocalDateTime,
        curDateTime: LocalDateTime
    ): Flow<Long?> {

        return flow {
            while (true) {
                emit(getHourToSleep(hour, futureDateTime, curDateTime))
                delay(1.minutes)
            }
        }.distinctUntilChanged()

    }

    private fun getHourToSleep(
        hour: Int,
        futureDateTime: LocalDateTime,
        curDateTime: LocalDateTime
    ): Long? {
        if (hour !in 4..10) {
            return null
        }

        val tomorrow = curDateTime.plusDays(1)

        // If future dayOfYear is not equals to dayOfYear tomorrow, we don't have to tell user when to sleep.
        // Also, if time is set to 10am tomorrow, the sleep time is 2am which is same day.
        if (tomorrow.dayOfYear != futureDateTime.dayOfYear && curDateTime.dayOfYear != futureDateTime.dayOfYear) {
            return null
        }

        val timeDiff = ChronoUnit.HOURS.between(curDateTime, futureDateTime)
        if (timeDiff < 8) {
            return null
        }

        return convertLocalDateTimeToEpochSeconds(futureDateTime.plusHours(-8))
    }

}
