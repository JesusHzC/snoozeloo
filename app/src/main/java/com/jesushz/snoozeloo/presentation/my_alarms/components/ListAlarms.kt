package com.jesushz.snoozeloo.presentation.my_alarms.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ListAlarms(
    modifier: Modifier = Modifier,
    alarms: List<String>
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = alarms,
            key = { it }
        ) { alarm ->
            AlarmCard()
        }
    }
}
