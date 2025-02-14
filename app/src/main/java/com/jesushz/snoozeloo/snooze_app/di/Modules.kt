package com.jesushz.snoozeloo.snooze_app.di

import com.jesushz.snoozeloo.snooze_app.data.repository.AlarmRepositoryImpl
import com.jesushz.snoozeloo.snooze_app.data.scheduler.AlarmSchedulerImpl
import com.jesushz.snoozeloo.snooze_app.data.use_case.GetFutureDateUseCaseImpl
import com.jesushz.snoozeloo.snooze_app.domain.repository.AlarmRepository
import com.jesushz.snoozeloo.snooze_app.domain.scheduler.AlarmScheduler
import com.jesushz.snoozeloo.snooze_app.domain.use_case.GetFutureDateUseCase
import com.jesushz.snoozeloo.snooze_app.presentation.MainViewModel
import com.jesushz.snoozeloo.snooze_app.presentation.audio.AudioViewModel
import com.jesushz.snoozeloo.snooze_app.presentation.my_alarms.MyAlarmsViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val snoozeViewModelModule = module {
    viewModelOf(::AudioViewModel)
    viewModelOf(::MyAlarmsViewModel)
    viewModelOf(::MainViewModel)
}

val snoozeRepositoryModule = module {
    singleOf(::AlarmRepositoryImpl).bind(AlarmRepository::class)
    singleOf(::AlarmSchedulerImpl).bind(AlarmScheduler::class)
}

val snoozeUseCaseModule = module {
    singleOf(::GetFutureDateUseCaseImpl).bind(GetFutureDateUseCase::class)
}
