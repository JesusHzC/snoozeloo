package com.jesushz.snoozeloo.core.di

import com.jesushz.snoozeloo.core.data.audio.AudioManagerImpl
import com.jesushz.snoozeloo.core.domain.audio.AudioManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreAudioModule = module {
    singleOf(::AudioManagerImpl).bind(AudioManager::class)
}
