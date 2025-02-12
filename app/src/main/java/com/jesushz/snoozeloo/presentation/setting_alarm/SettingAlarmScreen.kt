package com.jesushz.snoozeloo.presentation.setting_alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jesushz.snoozeloo.presentation.setting_alarm.components.CloseButton
import com.jesushz.snoozeloo.presentation.setting_alarm.components.SaveButton

@Composable
fun SettingAlarmScreenRoot() {
    SettingAlarmScreen()
}

@Composable
private fun SettingAlarmScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            )
            .systemBarsPadding()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CloseButton(
                onBackClick = {}
            )
            SaveButton(
                isEnabled = true,
                onButtonClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingAlarmScreenPreview() {
    MaterialTheme {
        SettingAlarmScreen()
    }
}