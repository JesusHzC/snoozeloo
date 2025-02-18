package com.jesushz.snoozeloo.snooze_app.presentation.audio

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesushz.snoozeloo.core.data.model.Ringtone
import com.jesushz.snoozeloo.core.domain.audio.AudioManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AudioViewModel(
    private val audioManager: AudioManager
): ViewModel() {

    private var cacheSounds: List<Ringtone> = emptyList()

    private val _state = MutableStateFlow(AudioState())
    val state = _state
        .onStart {
            if (cacheSounds.isEmpty()) {
                observeAvailableSounds()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )

    fun onAction(action: AudioAction) {
        when (action) {
            is AudioAction.OnAudioSelected -> {
                if (action.ringtone.uri == AudioManager.SILENT && audioManager.isPlaying()) {
                    audioManager.stop()
                    return
                }
                audioManager
                    .play(
                        uri = action.ringtone.uri,
                        volume = 1f,
                        isLooping = true
                    )
            }
            else -> Unit
        }
    }

    fun play(ringtone: Ringtone) {
        if (ringtone.uri == AudioManager.SILENT && audioManager.isPlaying()) {
            audioManager.stop()
            return
        }
        audioManager
            .play(
                uri = ringtone.uri,
                volume = 1f,
                isLooping = true
            )
    }

    fun stopEffects() {
        if (audioManager.isPlaying()) {
            audioManager.stop()
        }
    }

    private fun observeAvailableSounds() = viewModelScope.launch(Dispatchers.IO) {
        val sounds = audioManager
            .getAvailableSounds()

        _state.update {
            it.copy(
                availableSounds = sounds
            )
        }

        cacheSounds = sounds
    }
}
