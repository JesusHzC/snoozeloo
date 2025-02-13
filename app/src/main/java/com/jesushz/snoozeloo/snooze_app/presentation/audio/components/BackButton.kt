package com.jesushz.snoozeloo.snooze_app.presentation.audio.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(20)
            )
            .background(
                color = MaterialTheme.colorScheme.primary
            )
            .clickable {
                onBackClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowBack,
            contentDescription = stringResource(R.string.close),
            tint = Color.White
        )
    }
}

@Preview
@Composable
private fun BackButtonPreview() {
    MaterialTheme {
        BackButton(
            onBackClick = {}
        )
    }
}