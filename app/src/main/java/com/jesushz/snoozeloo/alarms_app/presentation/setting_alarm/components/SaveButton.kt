package com.jesushz.snoozeloo.alarms_app.presentation.setting_alarm.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.jesushz.snoozeloo.R
import com.jesushz.snoozeloo.core.presentation.theme.DisableButton
import com.jesushz.snoozeloo.core.presentation.theme.MontserratFamily

@Composable
fun SaveButton(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    isEnabled: Boolean = false,
    onButtonClick: () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = isEnabled,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = DisableButton,
            contentColor = Color.White,
            disabledContentColor = Color.White
        ),
        onClick = onButtonClick,
        contentPadding = contentPadding
    ) {
        Text(
            text = stringResource(R.string.save),
            fontFamily = MontserratFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SaveButtonPreview() {
    MaterialTheme {
        SaveButton(
            onButtonClick = {}
        )
    }
}