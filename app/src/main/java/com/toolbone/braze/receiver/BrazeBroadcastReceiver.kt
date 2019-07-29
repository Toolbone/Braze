package com.toolbone.braze.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.appboy.Constants
import com.appboy.push.AppboyNotificationUtils
import com.appboy.support.AppboyLogger
import java.util.concurrent.TimeUnit

class BrazeBroadcastReceiver : BroadcastReceiver(){

    private val TAG = AppboyLogger.getAppboyLogTag(BrazeBroadcastReceiver::class.java)

    override fun onReceive(context: Context, intent: Intent) {
        val packageName = context.packageName
        val pushReceivedAction = packageName + AppboyNotificationUtils.APPBOY_NOTIFICATION_RECEIVED_SUFFIX
        val notificationOpenedAction = packageName + AppboyNotificationUtils.APPBOY_NOTIFICATION_OPENED_SUFFIX
        val notificationDeletedAction = packageName + AppboyNotificationUtils.APPBOY_NOTIFICATION_DELETED_SUFFIX

        val action = intent.action
        Log.d(TAG, String.format("Received intent with action %s", action))

        logNotificationDuration(intent)

        when {
            pushReceivedAction == action -> Log.d(TAG, "Received push notification.")
            notificationOpenedAction == action -> AppboyNotificationUtils.routeUserWithNotificationOpenedIntent(context, intent)
            notificationDeletedAction == action -> Log.d(TAG, "Received push notification deleted intent.")
            else -> Log.d(TAG, String.format("Ignoring intent with unsupported action %s", action))
        }
    }

    /**
     * Logs the length of time elapsed since the notification's creation time.
     */
    private fun logNotificationDuration(intent: Intent) {
        // Log the duration of the push notification
        val extras = intent.extras
        if (extras != null && extras.containsKey(Constants.APPBOY_PUSH_RECEIVED_TIMESTAMP_MILLIS)) {
            val createdAt = extras.getLong(Constants.APPBOY_PUSH_RECEIVED_TIMESTAMP_MILLIS)
            val durationMillis = System.currentTimeMillis() - createdAt
            val durationSeconds = TimeUnit.MILLISECONDS.toSeconds(durationMillis)
            Log.i(TAG, "Notification active for $durationSeconds seconds.")
        }
    }
}