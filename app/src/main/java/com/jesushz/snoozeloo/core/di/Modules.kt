package com.jesushz.snoozeloo.core.di

import androidx.room.Room
import com.jesushz.snoozeloo.SnoozeApp
import com.jesushz.snoozeloo.core.data.audio.AudioManagerImpl
import com.jesushz.snoozeloo.core.data.database.SnoozeDatabase
import com.jesushz.snoozeloo.core.domain.audio.AudioManager
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single<CoroutineScope> {
        (androidApplication() as SnoozeApp).applicationScope
    }
}

val coreAudioModule = module {
    singleOf(::AudioManagerImpl).bind(AudioManager::class)
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            SnoozeDatabase::class.java,
            SnoozeDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }
    single { get<SnoozeDatabase>().alarmDao() }
}
