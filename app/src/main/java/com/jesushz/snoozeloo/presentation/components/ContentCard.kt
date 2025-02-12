package com.jesushz.snoozeloo.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ContentCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        color = Color.White,
        shape = RoundedCornerShape(10),
        content = content
    )
}

@Preview
@Composable
private fun ContentCardPreview() {
    MaterialTheme {
        ContentCard(
            modifier = Modifier
                .size(100.dp)
        ) {

        }
    }
}