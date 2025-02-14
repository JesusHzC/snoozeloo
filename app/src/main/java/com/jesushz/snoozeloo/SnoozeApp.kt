package com.jesushz.snoozeloo

import android.app.Application
import com.jesushz.snoozeloo.core.di.initKoin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class SnoozeApp: Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        initKoin(this@SnoozeApp)
    }

}