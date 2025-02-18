package com.jesushz.snoozeloo.snooze_app.presentation

import androidx.lifecycle.ViewModel
import com.jesushz.snoozeloo.core.data.model.Ringtone
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedRingtoneViewModel: ViewModel() {

    private val _selectedRingtone = MutableStateFlow<Ringtone?>(null)
    val selectedRingtone = _selectedRingtone.asStateFlow()

    fun onSelectedRingtone(ringtone: Ringtone) {
        _selectedRingtone.value = ringtone
    }

}
