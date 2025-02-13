package com.jesushz.snoozeloo.alarms_app.presentation.my_alarms.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jesushz.snoozeloo.R

@Composable
fun FloatingButton(
    modifier: Modifier = Modifier,
    onButtonClick: () -> Unit
) {
    FloatingActionButton(
        onClick = onButtonClick,
        modifier = modifier
            .size(60.dp),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = CircleShape
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.new_alarm),
            modifier = Modifier.size(38.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FloatingButtonPreview() {
    MaterialTheme {
        FloatingButton(
            onButtonClick = {}
        )
    }
}
