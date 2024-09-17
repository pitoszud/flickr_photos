package com.velocip.ybs

import android.app.Application
import com.velocip.ybs.network.BuildConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}