@file:OptIn(ExperimentalLayoutApi::class, ExperimentalLayoutApi::class)

package com.jesushz.snoozeloo.snooze_app.presentation.my_alarms.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesushz.snoozeloo.core.presentation.components.ContentCard
import com.jesushz.snoozeloo.core.presentation.components.DayCard
import com.jesushz.snoozeloo.core.presentation.theme.LightGray
import com.jesushz.snoozeloo.core.presentation.theme.MontserratFamily
import com.jesushz.snoozeloo.core.util.formatHourMinute
import com.jesushz.snoozeloo.core.util.formatSeconds
import com.jesushz.snoozeloo.core.util.formatSecondsToHourAndMinute
import com.jesushz.snoozeloo.core.util.getAmPm
import com.jesushz.snoozeloo.snooze_app.data.model.AlarmUi
import com.jesushz.snoozeloo.snooze_app.data.model.DayValue
import com.jesushz.snoozeloo.snooze_app.util.getDummyAlarm
import kotlin.math.min

@Composable
fun AlarmCard(
    modifier: Modifier = Modifier,
    alarmUi: AlarmUi,
    onAlarmClick: () -> Unit,
    onDeleteAlarmClick: () -> Unit,
    onToggleAlarm: () -> Unit,
    onToggleDayOfAlarm: (DayValue) -> Unit
) {
    val alarm = alarmUi.alarm
    val timeLeftInSeconds = alarmUi.timeLeftInSeconds
    val timeToSleepInSeconds = alarmUi.timeToSleepInSeconds
    ContentCard(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 300.dp),
        onCardClick = onAlarmClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = alarm.name,
                        fontFamily = MontserratFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = MontserratFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 42.sp
                                )
                            ) {
                                append(formatHourMinute(alarm.hour, alarm.minute))
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = MontserratFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 24.sp
                                )
                            ) {
                                append(" ${getAmPm(alarm.isMorning)}")
                            }
                        },
                        fontFamily = MontserratFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 42.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (alarm.enabled) {
                        val remainingTimeStr = formatSeconds(timeLeftInSeconds)

                        if (remainingTimeStr.isNotBlank()) {
                            Text(
                                text = "Alarm in $remainingTimeStr",
                                fontFamily = MontserratFamily,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                color = LightGray
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Switch(
                        checked = alarm.enabled,
                        onCheckedChange = {
                            onToggleAlarm()
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
            Spacer(modifier = Modifier.height(8.dp))
            var maxWidth by remember {
                mutableIntStateOf(0)
            }
            val maxWidthDp = with(LocalDensity.current) { maxWidth.toDp() }
            FlowRow(
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
                        isActivated = alarm.repeatDays.contains(day),
                        onDayClick = {
                            onToggleDayOfAlarm(day)
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
            Spacer(modifier = Modifier.height(8.dp))
            if (timeToSleepInSeconds != null && alarm.enabled) {
                Text(
                    text = "Go to bed at ${formatSecondsToHourAndMinute(timeToSleepInSeconds)} to get 8h of sleep",
                    fontFamily = MontserratFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = LightGray
                )
            }
        }
    }
}

@Preview
@Composable
private fun AlarmCardPreview() {
    MaterialTheme {
        AlarmCard(
            alarmUi = AlarmUi(
                alarm = getDummyAlarm(),
                timeLeftInSeconds = 3600,
                timeToSleepInSeconds = 3600
            ),
            onAlarmClick = {},
            onDeleteAlarmClick = {},
            onToggleAlarm = {},
            onToggleDayOfAlarm = {}
        )
    }
}