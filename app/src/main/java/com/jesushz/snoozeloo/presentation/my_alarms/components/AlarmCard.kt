@file:OptIn(ExperimentalLayoutApi::class, ExperimentalLayoutApi::class)

package com.jesushz.snoozeloo.presentation.my_alarms.components

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesushz.snoozeloo.presentation.components.ContentCard
import com.jesushz.snoozeloo.presentation.components.DayCard
import com.jesushz.snoozeloo.presentation.theme.LightGray
import com.jesushz.snoozeloo.presentation.theme.MontserratFamily

@Composable
fun AlarmCard(
    modifier: Modifier = Modifier
) {
   ContentCard(
       modifier = modifier
           .fillMaxWidth()
           .heightIn(max = 300.dp)
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
                       text = "Alarm 1",
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
                               append("10:00")
                           }
                           withStyle(
                               style = SpanStyle(
                                   fontFamily = MontserratFamily,
                                   fontWeight = FontWeight.Medium,
                                   fontSize = 24.sp
                               )
                           ) {
                               append(" AM")
                           }
                       },
                       fontFamily = MontserratFamily,
                       fontWeight = FontWeight.Medium,
                       fontSize = 42.sp
                   )
                   Spacer(modifier = Modifier.height(8.dp))
                   Text(
                       text = "Alarm in 30min",
                       fontFamily = MontserratFamily,
                       fontWeight = FontWeight.Medium,
                       fontSize = 14.sp,
                       color = LightGray
                   )
               }
               Box(
                   modifier = Modifier
                       .weight(1f),
                   contentAlignment = Alignment.TopEnd
               ) {
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
           Spacer(modifier = Modifier.height(8.dp))
           FlowRow(
               modifier = Modifier
                   .fillMaxWidth(),
               horizontalArrangement = Arrangement.spacedBy(4.dp)
           ) {
               repeat(7) {
                   DayCard(
                       isActivated = false,
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
           Spacer(modifier = Modifier.height(8.dp))
           Text(
               text = "Go to bed at 02:00AM to get 8h of sleep",
               fontFamily = MontserratFamily,
               fontWeight = FontWeight.Medium,
               fontSize = 14.sp,
               color = LightGray
           )
       }
   }
}

@Preview
@Composable
private fun AlarmCardPreview() {
    MaterialTheme {
        AlarmCard()
    }
}