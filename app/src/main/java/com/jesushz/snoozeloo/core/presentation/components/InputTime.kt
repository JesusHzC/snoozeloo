package com.jesushz.snoozeloo.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesushz.snoozeloo.core.presentation.theme.LightGray
import com.jesushz.snoozeloo.core.presentation.theme.MontserratFamily
import com.jesushz.snoozeloo.core.presentation.util.clearFocusOnKeyboardDismiss

@Composable
fun InputTime(
    modifier: Modifier = Modifier,
    text: String = "",
    hint: String = "00",
    maxLines: Int = 1,
    maxLength: Int = 2,
    onTextChange: (String) -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

    val textMeasurer = rememberTextMeasurer()
    val textStyle = TextStyle(
        color = MaterialTheme.colorScheme.primary,
        fontFamily = MontserratFamily,
        fontSize = 52.sp,
        fontWeight = FontWeight.Medium
    )
    val textWidth by remember(text) {
        derivedStateOf {
            textMeasurer.measure(text.ifEmpty { "|" }, textStyle).size.width.toFloat()
        }
    }
    val textWidthWithDensity = with(LocalDensity.current) { textWidth.toDp() }

    BasicTextField(
        modifier = modifier
            .border(
                width = 1.dp,
                color = if (isFocused)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(10)
            )
            .clip(RoundedCornerShape(10))
            .background(
                color = MaterialTheme.colorScheme.background
            )
            .onFocusChanged {
                isFocused = it.isFocused
            }
            .clearFocusOnKeyboardDismiss(),
        value = text,
        onValueChange = { value ->
            if (value.length <= maxLength) {
                onTextChange(value)
            }
        },
        textStyle = textStyle,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        maxLines = maxLines,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                when {
                    text.isEmpty() && !isFocused -> {
                        Text(
                            text = hint,
                            fontFamily = MontserratFamily,
                            fontSize = 52.sp,
                            fontWeight = FontWeight.Medium,
                            color = LightGray
                        )
                    }
                    else -> {
                        Box(
                            modifier = Modifier
                                .width(textWidthWithDensity),
                            contentAlignment = Alignment.Center
                        ) {
                            innerTextField()
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun InputTimePreview() {
    MaterialTheme {
        InputTime(
            modifier = Modifier.size(200.dp),
            onTextChange = {}
        )
    }
}