package com.jesushz.snoozeloo.snooze_app.presentation.setting_alarm

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jesushz.snoozeloo.core.data.model.Ringtone
import com.jesushz.snoozeloo.core.domain.audio.AudioManager
import com.jesushz.snoozeloo.snooze_app.data.model.Alarm
import com.jesushz.snoozeloo.snooze_app.domain.repository.AlarmRepository
import com.jesushz.snoozeloo.snooze_app.domain.use_case.GetFutureDateUseCase
import com.jesushz.snoozeloo.snooze_app.domain.use_case.GetTimeLeftInSecondsUseCase
import com.jesushz.snoozeloo.snooze_app.domain.use_case.ValidateAlarmUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.math.roundToInt

class SettingAlarmViewModel(
    private val alarmRepository: AlarmRepository,
    private val audioManager: AudioManager,
    private val validateAlarmUseCase: ValidateAlarmUseCase,
    private val getFutureDateUseCase: GetFutureDateUseCase,
    private val getTimeLeftInSecondsUseCase: GetTimeLeftInSecondsUseCase
): ViewModel() {

    private var cacheSounds: List<Ringtone> = emptyList()

    private val _channelEvent = Channel<SettingAlarmEvent>()
    val channelEvent = _channelEvent.receiveAsFlow()

    private val _state = MutableStateFlow(SettingAlarmState())
    val state = _state
        .onStart {
            val selectedAlarm = _state.value.alarmSelected
            if (selectedAlarm == null) {
                if (cacheSounds.isEmpty()) {
                    observeAvailableSounds(
                        onDefaultRingtone = { ringtone ->
                            ringtone?.let {
                                viewModelScope.launch {
                                    _channelEvent.send(SettingAlarmEvent.OnSelectedRingtone(it))
                                }
                            }
                        }
                    ).invokeOnCompletion {
                        observeHourMinutes()
                    }
                }
            } else {
                loadAlarmSelected(selectedAlarm)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )

    private val hourFlow = snapshotFlow { _state.value.hour }
    private val minutesFlow = snapshotFlow { _state.value.minutes }

    fun onAction(action: SettingAlarmAction) = when (action) {
        is SettingAlarmAction.OnAlarmRingtoneSelected -> {
            _state.update {
                it.copy(
                    alarmRingtone = action.ringtone
                )
            }
            observeHourMinutes()
        }
        is SettingAlarmAction.OnAlarmVibrate -> {
            _state.update {
                it.copy(
                    alarmVibrate = action.vibrate
                )
            }
        }
        is SettingAlarmAction.OnAlarmVolumeChange -> {
            _state.update {
                it.copy(
                    alarmVolume = action.volume
                )
            }
        }
        is SettingAlarmAction.OnHourChange -> {
            _state.update {
                it.copy(
                    hour = action.hour
                )
            }
            observeHourMinutes()
        }
        is SettingAlarmAction.OnMinutesChange -> {
            _state.update {
                it.copy(
                    minutes = action.minutes
                )
            }
            observeHourMinutes()
        }
        is SettingAlarmAction.OnNameChange -> {
            _state.update {
                it.copy(
                    name = action.name
                )
            }
        }
        is SettingAlarmAction.OnRepeatDaySelected -> {
            _state.update {
                it.copy(
                    repeatDays = it.repeatDays
                        .toMutableList()
                        .apply {
                            if (contains(action.day)) {
                                remove(action.day)
                            } else {
                                add(action.day)
                            }
                        }
                )
            }
            observeHourMinutes()
        }
        SettingAlarmAction.OnSaveClick -> {
            saveAlarm()
        }
        is SettingAlarmAction.OnAlarmSelected -> {
            _state.update {
                it.copy(
                    alarmSelected = action.alarm
                )
            }
        }
        else -> {
            Unit
        }
    }

    private fun loadAlarmSelected(alarm: Alarm) {
        _state.update {
            it.copy(
                alarmSelected = alarm,
                hour = alarm.hour.toString(),
                minutes = alarm.minute.toString(),
                name = alarm.name,
                repeatDays = alarm.repeatDays.toList(),
                alarmVolume = alarm.volume.toFloat(),
                alarmVibrate = alarm.vibrate
            )
        }
        observeAvailableSounds(
            isAlarmSelected = true,
            onDefaultRingtone = { ringtone ->
                ringtone?.let {
                    viewModelScope.launch {
                        _channelEvent.send(SettingAlarmEvent.OnSelectedRingtone(it))
                    }
                }
            }
        ).invokeOnCompletion {
            observeHourMinutes()
        }
    }

    private fun observeHourMinutes() {
        combine(hourFlow, minutesFlow) { hour, minutes ->
            val isValid = validateAlarmUseCase(hour, minutes)

            val repeatDays = _state.value.repeatDays.toMutableSet()
            val futureDateTime = getFutureDateUseCase(
                hour.toIntOrNull() ?: 0,
                minutes.toIntOrNull() ?: 0,
                repeatDays
            )

            if (isValid) {
                getTimeLeftInSecondsUseCase(futureDateTime).collectLatest { timeLeftInSeconds ->
                    updateState(true, timeLeftInSeconds)
                }
            } else {
                updateState(false, null)
            }
        }.launchIn(viewModelScope)
    }

    private fun updateState(isValid: Boolean, timeLeftInSeconds: Long?) {
        _state.update {
            it.copy(
                isSaveButtonEnabled = isValid && it.alarmRingtone != null,
                timeLeftInSeconds = timeLeftInSeconds
            )
        }
    }

    private fun saveAlarm() {
        viewModelScope.launch(Dispatchers.IO) {
            val alarmSelected = _state.value.alarmSelected
            if (alarmSelected != null) {
                val alarm = Alarm(
                    id = alarmSelected.id,
                    name = if (_state.value.name.isBlank()) "" else _state.value.name.trim(),
                    hour = _state.value.hour.toIntOrNull() ?: 0,
                    minute = _state.value.minutes.toIntOrNull() ?: 0,
                    enabled = true,
                    repeatDays = _state.value.repeatDays.toMutableSet(),
                    volume = _state.value.alarmVolume.roundToInt().coerceAtMost(100),
                    ringtoneUri = _state.value.alarmRingtone?.uri.orEmpty(),
                    vibrate = _state.value.alarmVibrate
                )
                alarmRepository.upsertAlarm(alarm)
                _state.update {
                    it.copy(
                        alarmSelected = null
                    )
                }
                _channelEvent.send(SettingAlarmEvent.OnSuccess)
            } else {
                val alarm = Alarm(
                    id = UUID.randomUUID().toString(),
                    name = if (_state.value.name.isBlank()) "" else _state.value.name.trim(),
                    hour = _state.value.hour.toIntOrNull() ?: 0,
                    minute = _state.value.minutes.toIntOrNull() ?: 0,
                    enabled = true,
                    repeatDays = _state.value.repeatDays.toMutableSet(),
                    volume = _state.value.alarmVolume.roundToInt().coerceAtMost(100),
                    ringtoneUri = _state.value.alarmRingtone?.uri.orEmpty(),
                    vibrate = _state.value.alarmVibrate
                )
                alarmRepository.upsertAlarm(alarm)
                _channelEvent.send(SettingAlarmEvent.OnSuccess)
            }
        }
    }

    private fun observeAvailableSounds(
        isAlarmSelected: Boolean = false,
        onDefaultRingtone: (Ringtone?) -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {
        val sounds = audioManager
            .getAvailableSounds()

        val defaultRingtone = if (isAlarmSelected) {
            val alarm = _state.value.alarmSelected
            alarm?.let {
                sounds.find { sound -> sound.uri == alarm.ringtoneUri }
            } ?: sounds.find { it.name.contains("Default") }
        } else {
            sounds.find { it.name.contains("Default") }
        }

        onDefaultRingtone(defaultRingtone)

        cacheSounds = sounds
    }

}