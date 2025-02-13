@file:OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)

package com.jesushz.snoozeloo.alarms_app.presentation.setting_alarm

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
import androidx.compose.runtime.mutableFloatStateOf
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
import com.jesushz.snoozeloo.R
import com.jesushz.snoozeloo.core.presentation.components.ContentCard
import com.jesushz.snoozeloo.core.presentation.components.DayCard
import com.jesushz.snoozeloo.core.presentation.components.InputTime
import com.jesushz.snoozeloo.core.presentation.theme.LightGray
import com.jesushz.snoozeloo.core.presentation.theme.MontserratFamily
import com.jesushz.snoozeloo.alarms_app.presentation.setting_alarm.components.CloseButton
import com.jesushz.snoozeloo.alarms_app.presentation.setting_alarm.components.SaveButton
import kotlin.math.min

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
        ActionButtons(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(
                    max = 32.dp
                )
        )
        Spacer(modifier = Modifier.padding(16.dp))
        TimeFields(
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        AlarmName(
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        AlarmRepeat(
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        AlarmRingTone(
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        AlarmVolume(
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        AlarmVibrate(
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun AlarmVibrate(
    modifier: Modifier = Modifier
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
                checked = true,
                onCheckedChange = {},
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
private fun AlarmVolume(modifier: Modifier = Modifier) {
    ContentCard(
        modifier = modifier
    ) {
        var sliderPosition by remember { mutableFloatStateOf(0f) }
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
                value = sliderPosition,
                onValueChange = {
                    sliderPosition = it
                    println("Value: $it")
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
private fun AlarmRingTone(modifier: Modifier = Modifier) {
    ContentCard(
        modifier = modifier
            .fillMaxWidth()
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
                text = "Default",
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = LightGray
            )
        }
    }
}

@Composable
private fun AlarmRepeat(modifier: Modifier = Modifier) {
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
                repeat(7) {
                    DayCard(
                        modifier = Modifier
                            .weight(1f)
                            .width(maxWidthDp)
                            .onSizeChanged {
                                maxWidth = min(maxWidth, it.width)
                            },
                        isActivated = true,
                        onDayClick = {}
                    ) {
                        Text(
                            text = "Th",
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
private fun AlarmName(modifier: Modifier = Modifier) {
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
                text = stringResource(R.string.alarm_name),
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = "Work",
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                color = LightGray
            )
        }
    }
}

@Composable
private fun ActionButtons(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CloseButton(
            modifier = Modifier
                .size(35.dp),
            onCloseClick = {}
        )
        SaveButton(
            isEnabled = true,
            contentPadding = PaddingValues(
                horizontal = 20.dp
            ),
            onButtonClick = {}
        )
    }
}

@Composable
private fun TimeFields(modifier: Modifier = Modifier) {
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
                var hour by remember {
                    mutableStateOf("")
                }
                var minute by remember {
                    mutableStateOf("")
                }
                InputTime(
                    modifier = Modifier
                        .weight(2f),
                    text = hour,
                    onTextChange = {
                        hour = it
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
                    text = minute,
                    onTextChange = {
                        minute = it
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Alarm in 7h 15min",
                fontFamily = MontserratFamily,
                color = LightGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
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