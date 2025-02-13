package com.jesushz.snoozeloo.alarms_app.presentation.audio

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jesushz.snoozeloo.alarms_app.presentation.audio.components.AudioCard
import com.jesushz.snoozeloo.alarms_app.presentation.audio.components.BackButton

@Composable
fun AudioScreenRoot() {
    AudioScreen()
}

@Composable
private fun AudioScreen() {
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
            onBackClick = {}
        )
        Spacer(modifier = Modifier.size(16.dp))
        val tmpSounds = listOf(
            "Sound 1", "Sound 2", "Sound 3", "Sound 4", "Sound 5", "Sound 6", "Sound 7", "Sound 8"
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = tmpSounds
            ) { sound ->
                AudioCard {  }
            }
        }
    }
}

@Preview
@Composable
private fun AudioScreenPreview() {
    MaterialTheme {
        AudioScreen()
    }
}