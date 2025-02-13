package com.jesushz.snoozeloo.snooze_app.presentation.audio

import com.jesushz.snoozeloo.core.data.model.Ringtone

data class AudioState(
    val availableSounds: List<Ringtone> = emptyList()
)
