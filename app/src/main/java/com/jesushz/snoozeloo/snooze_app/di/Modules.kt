package com.jesushz.snoozeloo.snooze_app.di

import com.jesushz.snoozeloo.snooze_app.data.repository.AlarmRepositoryImpl
import com.jesushz.snoozeloo.snooze_app.data.scheduler.AlarmSchedulerImpl
import com.jesushz.snoozeloo.snooze_app.data.use_case.GetFutureDateUseCaseImpl
import com.jesushz.snoozeloo.snooze_app.data.use_case.GetTimeLeftInSecondsUseCaseImpl
import com.jesushz.snoozeloo.snooze_app.data.use_case.GetTimeToSleepInSecondsUseCaseImpl
import com.jesushz.snoozeloo.snooze_app.data.use_case.ValidateAlarmUseCaseImpl
import com.jesushz.snoozeloo.snooze_app.domain.repository.AlarmRepository
import com.jesushz.snoozeloo.snooze_app.domain.scheduler.AlarmScheduler
import com.jesushz.snoozeloo.snooze_app.domain.use_case.GetFutureDateUseCase
import com.jesushz.snoozeloo.snooze_app.domain.use_case.GetTimeLeftInSecondsUseCase
import com.jesushz.snoozeloo.snooze_app.domain.use_case.GetTimeToSleepInSecondsUseCase
import com.jesushz.snoozeloo.snooze_app.domain.use_case.ValidateAlarmUseCase
import com.jesushz.snoozeloo.snooze_app.presentation.MainViewModel
import com.jesushz.snoozeloo.snooze_app.presentation.SelectedAlarmViewModel
import com.jesushz.snoozeloo.snooze_app.presentation.SelectedRingtoneViewModel
import com.jesushz.snoozeloo.snooze_app.presentation.audio.AudioViewModel
import com.jesushz.snoozeloo.snooze_app.presentation.my_alarms.MyAlarmsViewModel
import com.jesushz.snoozeloo.snooze_app.presentation.reminder.ReminderViewModel
import com.jesushz.snoozeloo.snooze_app.presentation.setting_alarm.SettingAlarmViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val snoozeViewModelModule = module {
    viewModelOf(::AudioViewModel)
    viewModelOf(::MyAlarmsViewModel)
    viewModelOf(::MainViewModel)
    viewModelOf(::SettingAlarmViewModel)
    viewModelOf(::ReminderViewModel)
    viewModelOf(::SelectedAlarmViewModel)
    viewModelOf(::SelectedRingtoneViewModel)
}

val snoozeRepositoryModule = module {
    singleOf(::AlarmRepositoryImpl).bind(AlarmRepository::class)
    singleOf(::AlarmSchedulerImpl).bind(AlarmScheduler::class)
}

val snoozeUseCaseModule = module {
    singleOf(::GetFutureDateUseCaseImpl).bind(GetFutureDateUseCase::class)
    singleOf(::GetTimeLeftInSecondsUseCaseImpl).bind(GetTimeLeftInSecondsUseCase::class)
    singleOf(::GetTimeToSleepInSecondsUseCaseImpl).bind(GetTimeToSleepInSecondsUseCase::class)
    singleOf(::ValidateAlarmUseCaseImpl).bind(ValidateAlarmUseCase::class)
}
