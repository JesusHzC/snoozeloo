package com.jesushz.snoozeloo.snooze_app.presentation.audio

import com.jesushz.snoozeloo.core.data.model.Ringtone

sealed interface AudioAction {
    data class OnAudioSelected(val ringtone: Ringtone): AudioAction
    data object OnBackClick: AudioAction
}
