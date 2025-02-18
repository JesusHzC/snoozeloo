package com.jesushz.snoozeloo.snooze_app.presentation.audio

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesushz.snoozeloo.core.data.model.Ringtone
import com.jesushz.snoozeloo.core.domain.audio.AudioManager.Companion.SILENT
import com.jesushz.snoozeloo.snooze_app.presentation.audio.components.AudioCard
import com.jesushz.snoozeloo.snooze_app.presentation.audio.components.BackButton
import org.koin.androidx.compose.koinViewModel

@Composable
fun AudioScreenRoot(
    viewModel: AudioViewModel = koinViewModel(),
    audioSelected: Ringtone? = null,
    onAudioSelected: (Ringtone) -> Unit,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    AudioScreen(
        state = state,
        audioSelected = audioSelected,
        onAction = {  action ->
            when (action) {
                is AudioAction.OnAudioSelected -> {
                    viewModel.play(action.ringtone)
                    onAudioSelected(action.ringtone)
                }
                AudioAction.OnBackClick -> {
                    viewModel.stopEffects()
                    onNavigateBack()
                }
            }
        }
    )
}

@Composable
private fun AudioScreen(
    state: AudioState,
    audioSelected: Ringtone? = null,
    onAction: (AudioAction) -> Unit
) {
    BackHandler {
        onAction(AudioAction.OnBackClick)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            )
            .systemBarsPadding()
            .padding(16.dp)
    ) {
        BackButton(
            modifier = Modifier
                .size(32.dp),
            onBackClick = {
                onAction(AudioAction.OnBackClick)
            }
        )
        Spacer(modifier = Modifier.size(16.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = state.availableSounds
            ) { sound ->
                AudioCard(
                    ringtone = sound,
                    isSilentMode = sound.uri == SILENT,
                    isSelected = sound == audioSelected,
                    onSoundClick = {
                        onAction(AudioAction.OnAudioSelected(sound))
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun AudioScreenPreview() {
    MaterialTheme {
        AudioScreen(
            state = AudioState(
                availableSounds = listOf(
                    Ringtone("Silent", SILENT),
                    Ringtone("Default", ""),
                    Ringtone("Sound", ""),
                )
            ),
            onAction = {}
        )
    }
}