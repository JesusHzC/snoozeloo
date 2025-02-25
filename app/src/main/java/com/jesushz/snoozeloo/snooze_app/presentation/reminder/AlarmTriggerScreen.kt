package com.jesushz.snoozeloo.snooze_app.presentation.reminder

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesushz.snoozeloo.R
import com.jesushz.snoozeloo.core.presentation.theme.SnoozelooTheme
import com.jesushz.snoozeloo.core.util.formatHourMinute
import com.jesushz.snoozeloo.snooze_app.data.model.Alarm
import com.jesushz.snoozeloo.snooze_app.util.getDummyAlarm

@Composable
fun AlarmTriggerScreen(
    alarm: Alarm,
    onTurnOffClick: () -> Unit,
    onSnoozeClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_alarm_blue),
            contentDescription = null,
            modifier = Modifier.size(55.5.dp),
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = formatHourMinute(alarm.hour, alarm.minute),
            style = MaterialTheme.typography.displayMedium,
            fontSize = 82.sp,
            lineHeight = 99.9.sp,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = alarm.name.ifBlank { "Alarm" }.uppercase(),
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 24.sp,
            lineHeight = 29.2.sp
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onTurnOffClick,
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text(
                text = "Turn Off",
                modifier = Modifier.padding(
                    vertical = 8.dp,
                    horizontal = 32.dp
                ),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 24.sp,
                lineHeight = 29.2.sp
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(
            onClick = onSnoozeClick,
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0xffECEFFF)
            ),
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text(
                text = "Snooze for 5 min",
                modifier = Modifier.padding(
                    vertical = 8.dp,
                    horizontal = 32.dp
                ),
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 24.sp,
                lineHeight = 29.2.sp
            )
        }
    }
}

@Preview
@Composable
private fun AlarmTriggerScreenPreview() {
    SnoozelooTheme {
        AlarmTriggerScreen(
            alarm = getDummyAlarm(),
            onTurnOffClick = {},
            onSnoozeClick = {}
        )
    }
}
