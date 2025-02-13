package com.jesushz.snoozeloo.snooze_app.presentation.my_alarms

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MyAlarmsViewModel: ViewModel() {

    private val _state = MutableStateFlow(MyAlarmsState())
    val state = _state.asStateFlow()


}
