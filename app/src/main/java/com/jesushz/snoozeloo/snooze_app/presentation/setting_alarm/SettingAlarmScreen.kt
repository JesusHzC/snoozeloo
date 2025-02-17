@file:OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)

package com.jesushz.snoozeloo.snooze_app.presentation.setting_alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jesushz.snoozeloo.R
import com.jesushz.snoozeloo.core.data.model.Ringtone
import com.jesushz.snoozeloo.core.presentation.components.ContentCard
import com.jesushz.snoozeloo.core.presentation.components.DayCard
import com.jesushz.snoozeloo.core.presentation.components.InputTime
import com.jesushz.snoozeloo.core.presentation.theme.LightGray
import com.jesushz.snoozeloo.core.presentation.theme.MontserratFamily
import com.jesushz.snoozeloo.core.presentation.util.ObserveAsEvents
import com.jesushz.snoozeloo.core.util.formatSeconds
import com.jesushz.snoozeloo.snooze_app.data.model.DayValue
import com.jesushz.snoozeloo.snooze_app.presentation.setting_alarm.components.AlarmNameDialog
import com.jesushz.snoozeloo.snooze_app.presentation.setting_alarm.components.CloseButton
import com.jesushz.snoozeloo.snooze_app.presentation.setting_alarm.components.SaveButton
import org.koin.androidx.compose.koinViewModel
import kotlin.math.min

@Composable
fun SettingAlarmScreenRoot(
    viewModel: SettingAlarmViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToAudio: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvents(
        flow = viewModel.channelEvent
    ) { event ->
        when (event) {
            SettingAlarmEvent.OnSuccess -> {
                onNavigateBack()
            }
        }
    }
    SettingAlarmScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is SettingAlarmAction.OnBackClick -> {
                    onNavigateBack()
                }
                is SettingAlarmAction.OnNavigateToAudio -> {
                    onNavigateToAudio()
                }
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
private fun SettingAlarmScreen(
    state: SettingAlarmState,
    onAction: (SettingAlarmAction) -> Unit,
) {
    var showAlarmNameDialog by remember {
        mutableStateOf(false)
    }
    AlarmNameDialog(
        showDialog = showAlarmNameDialog,
        name = state.name,
        onNameChange = {
            onAction(SettingAlarmAction.OnNameChange(it))
        },
        onDismiss = {
            showAlarmNameDialog = false
        },
        onSaveClick = {
            showAlarmNameDialog = false
        }
    )
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
        ActionButtons(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(
                    max = 32.dp
                ),
            saveButtonIsEnabled = state.isSaveButtonEnabled,
            onSaveButtonClick = {
                onAction(SettingAlarmAction.OnSaveClick)
            },
            onCloseButtonClick = {
                onAction(SettingAlarmAction.OnBackClick)
            }
        )
        Spacer(modifier = Modifier.padding(16.dp))
        TimeFields(
            modifier = Modifier
                .fillMaxWidth(),
            minuteText = state.minutes,
            hourText = state.hour,
            onHourChange = {
                onAction(SettingAlarmAction.OnHourChange(it))
            },
            onMinuteChange = {
                onAction(SettingAlarmAction.OnMinutesChange(it))
            },
            timeLeftInSeconds = state.timeLeftInSeconds
        )
        Spacer(modifier = Modifier.height(16.dp))
        AlarmName(
            modifier = Modifier
                .fillMaxWidth(),
            alarmName = state.name,
            onNameCardClicked = {
                showAlarmNameDialog = true
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        AlarmRepeat(
            modifier = Modifier
                .fillMaxWidth(),
            daysSelected = state.repeatDays,
            onDaySelected = {
                onAction(SettingAlarmAction.OnRepeatDaySelected(it))
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (state.alarmRingtone != null) {
            AlarmRingTone(
                modifier = Modifier
                    .fillMaxWidth(),
                onSoundClick = {
                    onAction(SettingAlarmAction.OnNavigateToAudio)
                },
                currentRingtone = state.alarmRingtone
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        AlarmVolume(
            modifier = Modifier
                .fillMaxWidth(),
            currentVolume = state.alarmVolume,
            onVolumeChange = {
                onAction(SettingAlarmAction.OnAlarmVolumeChange(it))
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        AlarmVibrate(
            modifier = Modifier
                .fillMaxWidth(),
            isVibrateEnabled = state.alarmVibrate,
            onVibrateChange = {
                onAction(SettingAlarmAction.OnAlarmVibrate(it))
            }
        )
    }
}

@Composable
fun AlarmVibrate(
    modifier: Modifier = Modifier,
    isVibrateEnabled: Boolean,
    onVibrateChange: (Boolean) -> Unit
) {
    ContentCard(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.alarm_vibrate),
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Switch(
                checked = isVibrateEnabled,
                onCheckedChange = {
                    onVibrateChange(it)
                },
                colors = SwitchDefaults.colors(
                    uncheckedBorderColor = MaterialTheme.colorScheme.secondary,
                    uncheckedTrackColor = MaterialTheme.colorScheme.secondary,
                    uncheckedThumbColor = MaterialTheme.colorScheme.surface,
                    uncheckedIconColor = MaterialTheme.colorScheme.surface,
                ),
                thumbContent = {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                color = MaterialTheme.colorScheme.surface
                            )
                            .size(24.dp)
                    )
                }
            )
        }
    }
}

@Composable
private fun AlarmVolume(
    modifier: Modifier = Modifier,
    currentVolume: Float,
    onVolumeChange: (Float) -> Unit
) {
    ContentCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.alarm_volume),
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Slider(
                value = currentVolume,
                onValueChange = {
                    onVolumeChange(it)
                },
                valueRange = 0f..100f,
                thumb = {
                    Box(
                        modifier = Modifier
                            .size(25.dp)
                            .clip(CircleShape)
                            .background(
                                color = MaterialTheme.colorScheme.primary
                            )
                    )
                },
                track = { sliderState ->
                    SliderDefaults.Track(
                        sliderState = sliderState,
                        thumbTrackGapSize = 0.dp,
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                    )
                }
            )
        }
    }
}

@Composable
private fun AlarmRingTone(
    modifier: Modifier = Modifier,
    currentRingtone: Ringtone,
    onSoundClick: () -> Unit
) {
    ContentCard(
        modifier = modifier
            .fillMaxWidth(),
        onCardClick = onSoundClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.alarm_ringtone),
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = currentRingtone.name,
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = LightGray
            )
        }
    }
}

@Composable
private fun AlarmRepeat(
    modifier: Modifier = Modifier,
    daysSelected: List<DayValue>,
    onDaySelected: (DayValue) -> Unit
) {
    ContentCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.repeat),
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            var maxWidth by remember {
                mutableIntStateOf(0)
            }
            val maxWidthDp = with(LocalDensity.current) { maxWidth.toDp() }
            FlowRow (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                DayValue.entries.forEach { day ->
                    DayCard(
                        modifier = Modifier
                            .weight(1f)
                            .width(maxWidthDp)
                            .onSizeChanged {
                                maxWidth = min(maxWidth, it.width)
                            },
                        isActivated = daysSelected.contains(day),
                        onDayClick = {
                            onDaySelected(day)
                        }
                    ) {
                        Text(
                            text = day.value,
                            fontFamily = MontserratFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AlarmName(
    modifier: Modifier = Modifier,
    alarmName: String,
    onNameCardClicked: () -> Unit
) {
    ContentCard(
        modifier = modifier,
        onCardClick = {
            onNameCardClicked()
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.alarm_name),
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = alarmName,
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = LightGray
            )
        }
    }
}

@Composable
private fun ActionButtons(
    modifier: Modifier = Modifier,
    saveButtonIsEnabled: Boolean,
    onCloseButtonClick: () -> Unit,
    onSaveButtonClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CloseButton(
            modifier = Modifier
                .size(35.dp),
            onCloseClick = onCloseButtonClick
        )
        SaveButton(
            isEnabled = saveButtonIsEnabled,
            contentPadding = PaddingValues(
                horizontal = 20.dp
            ),
            onButtonClick = onSaveButtonClick
        )
    }
}

@Composable
private fun TimeFields(
    modifier: Modifier = Modifier,
    timeLeftInSeconds: Long? = null,
    hourText: String,
    minuteText: String,
    onHourChange: (String) -> Unit,
    onMinuteChange: (String) -> Unit
) {
    ContentCard(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InputTime(
                    modifier = Modifier
                        .weight(2f),
                    text = hourText,
                    onTextChange = {
                        onHourChange(it)
                    }
                )
                Text(
                    text = stringResource(R.string.time_separator),
                    color = LightGray,
                    fontFamily = MontserratFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(0.5f)
                )
                InputTime(
                    modifier = Modifier
                        .weight(2f),
                    text = minuteText,
                    onTextChange = {
                        onMinuteChange(it)
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            if (timeLeftInSeconds != null) {
                val remainingTimeStr = formatSeconds(timeLeftInSeconds)
                if (remainingTimeStr.isNotBlank()) {
                    Text(
                        text = "Alarm in $remainingTimeStr",
                        fontFamily = MontserratFamily,
                        color = LightGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingAlarmScreenPreview() {
    MaterialTheme {
        SettingAlarmScreen(
            state = SettingAlarmState(),
            onAction = {}
        )
    }
}