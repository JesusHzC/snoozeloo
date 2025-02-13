package com.jesushz.snoozeloo.snooze_app.di

import com.jesushz.snoozeloo.snooze_app.presentation.audio.AudioViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val snoozeViewModelModule = module {
    viewModelOf(::AudioViewModel)
}
