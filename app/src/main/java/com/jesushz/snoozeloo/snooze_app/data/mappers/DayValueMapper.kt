package com.jesushz.snoozeloo.snooze_app.data.mappers

import com.jesushz.snoozeloo.snooze_app.data.model.DayValue

fun Set<Int>.toDayValues(): Set<DayValue> {
    return this.map { DayValue.entries[it] }.toSet()
}

fun Set<DayValue>.toSetOfInt(): Set<Int> {
    return this.map { it.ordinal }.toSet()
}
