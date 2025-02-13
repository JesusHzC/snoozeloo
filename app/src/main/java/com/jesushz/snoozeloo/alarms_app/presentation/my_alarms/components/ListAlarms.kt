package com.jesushz.snoozeloo.alarms_app.presentation.my_alarms.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

@Preview(showBackground = true)
@Composable
private fun ListAlarmsPreview() {
    MaterialTheme {
        ListAlarms(
            alarms = (1..20).map { it.toString() }
        )
    }
}
