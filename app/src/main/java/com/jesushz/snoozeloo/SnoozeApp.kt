package com.jesushz.snoozeloo

import android.app.Application
import com.jesushz.snoozeloo.core.di.initKoin

class SnoozeApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(this@SnoozeApp)
    }

}