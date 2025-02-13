package com.jesushz.snoozeloo.snooze_app.presentation.setting_alarm.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jesushz.snoozeloo.R
import com.jesushz.snoozeloo.core.presentation.theme.DisableButton

@Composable
fun CloseButton(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(20)
            )
            .background(
                color = DisableButton
            )
            .clickable {
                onCloseClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(R.string.close),
            tint = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CloseButtonPreview() {
    MaterialTheme {
        CloseButton(
            onCloseClick = {}
        )
    }
}