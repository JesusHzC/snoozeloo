package com.jesushz.snoozeloo.snooze_app.presentation.audio.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesushz.snoozeloo.R
import com.jesushz.snoozeloo.core.presentation.components.ContentCard
import com.jesushz.snoozeloo.core.presentation.theme.MontserratFamily

@Composable
fun AudioCard(
    modifier: Modifier = Modifier,
    isSilentMode: Boolean = false,
    isSelected: Boolean = false,
    onSoundClick: () -> Unit
) {
    ContentCard(
        modifier = modifier,
        onCardClick = onSoundClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(
                        color = MaterialTheme.colorScheme.background
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = if (isSilentMode)
                        painterResource(R.drawable.ic_notification_silent)
                    else
                        painterResource(R.drawable.ic_notification_ringing),
                    contentDescription = null
                )
            }
            Text(
                text = "Silent",
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            )
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(
                            color = MaterialTheme.colorScheme.primary
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.background,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun AudioCardPreview() {
    MaterialTheme {
        AudioCard(
            isSelected = true,
            onSoundClick = {}
        )
    }
}