package com.jesushz.snoozeloo.snooze_app.data.use_case

import com.jesushz.snoozeloo.snooze_app.data.model.DayValue
import com.jesushz.snoozeloo.snooze_app.domain.use_case.GetFutureDateUseCase
import java.time.DayOfWeek
import java.time.LocalDateTime

class GetFutureDateUseCaseImpl: GetFutureDateUseCase {

    override fun invoke(
        hour: Int,
        minute: Int,
        repeatDays: Set<DayValue>,
        curDateTime: LocalDateTime
    ): LocalDateTime {
        val futureDateTime = getFutureDateWithRepeatDays(
            curDateTime,
            hour,
            minute,
            repeatDays
        )

        return futureDateTime
    }

    private fun getFutureDateWithRepeatDays(
        curDateTime: LocalDateTime,
        hour: Int,
        minute: Int,
        repeatDays: Set<DayValue>
    ): LocalDateTime {
        var futureDateTime: LocalDateTime = curDateTime
        val isRepeatable = repeatDays.isNotEmpty()

        if (isRepeatable) {
            while (!isDayOfWeekPresentInRepeatDays(futureDateTime.dayOfWeek, repeatDays)) {
                futureDateTime = futureDateTime.plusDays(1)
            }
        }

        return if (curDateTime.dayOfYear != futureDateTime.dayOfYear) {
            futureDateTime
                .withHour(hour)
                .withMinute(minute)
                .withSecond(0)
        } else {
            val tomorrow = curDateTime.plusDays(1)
            val isTomorrowAvailable = if (isRepeatable) {
                isDayOfWeekPresentInRepeatDays(tomorrow.dayOfWeek, repeatDays)
            } else {
                true
            }


            if ((hour >= curDateTime.hour && minute > curDateTime.minute) || hour > curDateTime.hour) {
                curDateTime
                    .withHour(hour)
                    .withMinute(minute)
                    .withSecond(0)
            } else if (isTomorrowAvailable) {
                tomorrow
                    .withHour(hour)
                    .withMinute(minute)
                    .withSecond(0)
            } else {
                getFutureDateWithRepeatDays(tomorrow, hour, minute, repeatDays)
            }
        }
    }

    private fun isDayOfWeekPresentInRepeatDays(dayOfWeek: DayOfWeek, repeatDays: Set<DayValue>): Boolean {
        return when (dayOfWeek) {
            DayOfWeek.MONDAY -> repeatDays.contains(DayValue.MONDAY)
            DayOfWeek.TUESDAY -> repeatDays.contains(DayValue.TUESDAY)
            DayOfWeek.WEDNESDAY -> repeatDays.contains(DayValue.WEDNESDAY)
            DayOfWeek.THURSDAY -> repeatDays.contains(DayValue.THURSDAY)
            DayOfWeek.FRIDAY -> repeatDays.contains(DayValue.FRIDAY)
            DayOfWeek.SATURDAY -> repeatDays.contains(DayValue.SATURDAY)
            DayOfWeek.SUNDAY -> repeatDays.contains(DayValue.SUNDAY)
        }
    }

}
