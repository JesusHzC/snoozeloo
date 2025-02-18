package com.jesushz.snoozeloo.snooze_app.presentation

import androidx.lifecycle.ViewModel
import com.jesushz.snoozeloo.snooze_app.data.model.Alarm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedAlarmViewModel: ViewModel() {

    private val _selectedAlarm = MutableStateFlow<Alarm?>(null)
    val selectedAlarm = _selectedAlarm.asStateFlow()

    fun onSelectedAlarm(alarm: Alarm?) {
        _selectedAlarm.value = alarm
    }

}
