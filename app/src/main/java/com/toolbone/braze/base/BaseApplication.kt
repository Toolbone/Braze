package com.toolbone.braze.base

import android.app.Application
import android.util.Log
import com.appboy.AppboyLifecycleCallbackListener
import com.appboy.Appboy
import com.appboy.configuration.AppboyConfig
import com.appboy.support.AppboyLogger



class BaseApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        AppboyLogger.setLogLevel(Log.VERBOSE)

        val appboyConfig = AppboyConfig.Builder()
            .setDefaultNotificationChannelName("Appboy Push")
            .setDefaultNotificationChannelDescription("Appboy related push")
        Appboy.configure(this, appboyConfig.build())

        registerActivityLifecycleCallbacks(AppboyLifecycleCallbackListener())
    }

}