package com.jesushz.snoozeloo.snooze_app.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.jesushz.snoozeloo.core.presentation.components.Route
import com.jesushz.snoozeloo.core.presentation.theme.SnoozelooTheme
import com.jesushz.snoozeloo.core.presentation.util.observeAsEvents
import com.jesushz.snoozeloo.core.util.isTiramisuPlus
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel = getViewModel()
        var mainState = MainState()
        observeAsEvents(
            flow = viewModel.state
        ) {
            mainState = it
        }
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                mainState.showSplashScreen
            }
        }
        enableEdgeToEdge()

        if (isTiramisuPlus()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                0
            )
        }

        setContent {
            SnoozelooTheme {
                val navController = rememberNavController()

                NavigationRoot(
                    navController = navController,
                    startDestination = Route.SnoozeGraph
                )
            }
        }
    }
}
