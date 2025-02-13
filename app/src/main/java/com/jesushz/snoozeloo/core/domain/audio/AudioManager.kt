package com.jesushz.snoozeloo.core.domain.audio

import com.jesushz.snoozeloo.core.data.model.Ringtone

interface AudioManager {

    suspend fun getAvailableSounds(): List<Ringtone>

    fun play(
        uri: String,
        volume: Float,
        isLooping: Boolean = false
    )

    fun stop()

    fun isPlaying(): Boolean

    companion object {
        const val SILENT = "silent"
        const val ALARM_MAX_REMINDER_MILLIS = 300_000L
    }
}
