package com.jesushz.snoozeloo.snooze_app.presentation.my_alarms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesushz.snoozeloo.R
import com.jesushz.snoozeloo.snooze_app.presentation.my_alarms.components.EmptyAlarms
import com.jesushz.snoozeloo.snooze_app.presentation.my_alarms.components.FloatingButton
import com.jesushz.snoozeloo.snooze_app.presentation.my_alarms.components.ListAlarms
import com.jesushz.snoozeloo.core.presentation.theme.MontserratFamily
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyAlarmsScreenRoot(
    viewModel: MyAlarmsViewModel = koinViewModel(),
    onNavigateToSettingAlarm: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MyAlarmsScreen(
        state = state,
        onNavigateToSettingAlarm = onNavigateToSettingAlarm
    )
}

@Composable
private fun MyAlarmsScreen(
    state: MyAlarmsState,
    onNavigateToSettingAlarm: () -> Unit = {}
) {
    Scaffold(
        floatingActionButton = {
            FloatingButton(
                onButtonClick = onNavigateToSettingAlarm
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.your_alarms),
                fontFamily = MontserratFamily,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
            when {
                state.alarms.isEmpty() -> {
                    EmptyAlarms(
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
                else -> {
                    ListAlarms(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(top = 64.dp),
                        alarms = (1..20).map { it.toString() },
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MyAlarmsScreenPreview() {
    MaterialTheme {
        MyAlarmsScreen(
            state = MyAlarmsState(alarms = emptyList())
        )
    }
}
