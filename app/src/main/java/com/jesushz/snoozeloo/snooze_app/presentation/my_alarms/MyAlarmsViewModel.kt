@file:OptIn(ExperimentalCoroutinesApi::class)

package com.jesushz.snoozeloo.snooze_app.presentation.my_alarms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesushz.snoozeloo.snooze_app.data.model.Alarm
import com.jesushz.snoozeloo.snooze_app.data.model.AlarmUi
import com.jesushz.snoozeloo.snooze_app.domain.repository.AlarmRepository
import com.jesushz.snoozeloo.snooze_app.domain.use_case.GetFutureDateUseCase
import com.jesushz.snoozeloo.snooze_app.domain.use_case.GetTimeLeftInSecondsUseCase
import com.jesushz.snoozeloo.snooze_app.domain.use_case.GetTimeToSleepInSecondsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyAlarmsViewModel(
    private val alarmRepository: AlarmRepository,
    private val getFutureDateUseCase: GetFutureDateUseCase,
    private val getTimeLeftInSecondsUseCase: GetTimeLeftInSecondsUseCase,
    private val getTimeToSleepInSecondsUseCase: GetTimeToSleepInSecondsUseCase
): ViewModel() {

    private var cacheAlarmList = listOf<AlarmUi>()

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

    fun onAction(action: MyAlarmsAction) {
        when (action) {
            is MyAlarmsAction.OnDeleteAlarm -> {
                viewModelScope.launch(Dispatchers.IO) {
                    alarmRepository.deleteAlarmById(action.alarm.id)
                }
            }
            is MyAlarmsAction.OnToggleAlarm -> {
                viewModelScope.launch(Dispatchers.IO) {
                    alarmRepository.toggle(action.alarm)
                }
            }
            is MyAlarmsAction.OnToggleDayOfAlarm -> {
                viewModelScope.launch(Dispatchers.IO) {
                    alarmRepository.toggleDay(action.day, action.alarm)
                }
            }
            else -> Unit
        }
    }

    private fun observeAlarmsList() {
        alarmRepository
            .getAllAlarms()
            .flatMapLatest { alarms ->
                val alarmUiFlow = alarms
                    .sortedWith(compareBy<Alarm> { it.hour }.thenBy { it.minute })
                    .map { alarm ->
                        val futureDateTime = getFutureDateUseCase(alarm.hour, alarm.minute, alarm.repeatDays)
                        val timeLeftInSeconds = getTimeLeftInSecondsUseCase(futureDateTime)
                        val timeToSleepInSeconds = getTimeToSleepInSecondsUseCase(alarm.hour, futureDateTime)

                        combine(timeLeftInSeconds, timeToSleepInSeconds) { timeLeft, timeToSleep ->
                            AlarmUi(alarm, timeLeft, timeToSleep)
                        }
                    }
                combine(alarmUiFlow) { it.toList() }
            }.onEach {
                cacheAlarmList = it
                _state.update { state ->
                    state.copy(
                        alarms = it
                    )
                }
            }
            .launchIn(viewModelScope)
    }

}
