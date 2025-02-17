package com.jesushz.snoozeloo.snooze_app.presentation.setting_alarm.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jesushz.snoozeloo.R
import com.jesushz.snoozeloo.core.presentation.theme.MontserratFamily
import com.jesushz.snoozeloo.core.presentation.util.clearFocusOnKeyboardDismiss

@Composable
fun AlarmNameDialog(
    showDialog: Boolean,
    name: String,
    hint: String = "Alarm Name",
    onNameChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSaveClick: () -> Unit
) {
    var nameTmp by remember {
        mutableStateOf("")
    }
    if (showDialog) {
        Dialog(
            onDismissRequest = {
                nameTmp = name
                onDismiss()
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.9f),
                color = Color.White,
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.alarm_name),
                        fontFamily = MontserratFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    var isFocused by remember {
                        mutableStateOf(false)
                    }
                    BasicTextField(
                        value = nameTmp,
                        onValueChange = {
                            nameTmp = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .border(
                                width = 1.dp,
                                color = if (isFocused)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.background,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .onFocusChanged {
                                isFocused = it.isFocused
                            }
                            .clearFocusOnKeyboardDismiss(),
                        cursorBrush = SolidColor(Color.Black),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        textStyle = TextStyle(
                            fontFamily = MontserratFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        ),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                if (name.isEmpty() && !isFocused) {
                                    Text(
                                        text = hint,
                                        fontFamily = MontserratFamily,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                } else {
                                    innerTextField()
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    SaveButton(
                        modifier = Modifier
                            .align(Alignment.End),
                        isEnabled = nameTmp.isNotEmpty(),
                        contentPadding = PaddingValues(
                            vertical = 0.dp,
                            horizontal = 16.dp
                        )
                    ) {
                        onNameChange(nameTmp)
                        onSaveClick()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AlarmNameDialogPreview() {
    MaterialTheme {
        AlarmNameDialog(
            showDialog = true,
            name = "",
            onNameChange = {},
            onDismiss = {},
            onSaveClick = {}
        )
    }
}