package com.jesushz.snoozeloo.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DayCard(
    modifier: Modifier = Modifier,
    isActivated: Boolean,
    onDayClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(50),
        color = if (isActivated)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
        contentColor = if (isActivated)
            Color.White
        else
            Color.Black,
        onClick = onDayClick
    ) {
        Box(
            modifier = Modifier
                .padding(
                    horizontal = 12.dp,
                    vertical = 4.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun DayCardPreview() {
    MaterialTheme {
        DayCard(
            isActivated = false,
            onDayClick = {}
        ) {
            Text(
                text = "Th"
            )
        }
    }
}
