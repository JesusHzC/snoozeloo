package com.jesushz.snoozeloo.snooze_app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state
        .onStart {
            setupSplashScreen()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )

    private fun setupSplashScreen() {
        viewModelScope.launch {
            delay(1000L)
            _state.update {
                it.copy(
                    showSplashScreen = false
                )
            }
        }
    }

}
