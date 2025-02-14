package com.jesushz.snoozeloo.core.di

import android.content.Context
import com.jesushz.snoozeloo.snooze_app.di.snoozeRepositoryModule
import com.jesushz.snoozeloo.snooze_app.di.snoozeUseCaseModule
import com.jesushz.snoozeloo.snooze_app.di.snoozeViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

fun initKoin(ctx: Context) {
    startKoin {
        androidLogger()
        androidContext(ctx)
        modules(
            listOf(
                appModule,
                coreAudioModule,
                snoozeViewModelModule,
                databaseModule,
                snoozeRepositoryModule,
                snoozeUseCaseModule
            )
        )
    }
}
