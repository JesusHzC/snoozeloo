package com.jesushz.snoozeloo.snooze_app.presentation.my_alarms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesushz.snoozeloo.snooze_app.data.model.Alarm
import com.jesushz.snoozeloo.snooze_app.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyAlarmsViewModel(
    private val alarmRepository: AlarmRepository
): ViewModel() {

    private var cacheAlarmList = listOf<Alarm>()

    private val _state = MutableStateFlow(MyAlarmsState())
    val state = _state
        .onStart {
            if (cacheAlarmList.isEmpty()) {
                observeAlarmsList()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )

    private fun observeAlarmsList() {
        viewModelScope.launch {
            alarmRepository
                .getAllAlarms()
                .collect { alarms ->
                    cacheAlarmList = alarms
                    _state.update {
                        it.copy(
                            alarms = alarms
                        )
                    }
                }
        }
    }

}
