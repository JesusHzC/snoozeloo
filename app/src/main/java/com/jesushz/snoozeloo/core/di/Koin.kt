package com.jesushz.snoozeloo.core.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

fun initKoin(ctx: Context) {
    startKoin {
        androidLogger()
        androidContext(ctx)
    }
}
