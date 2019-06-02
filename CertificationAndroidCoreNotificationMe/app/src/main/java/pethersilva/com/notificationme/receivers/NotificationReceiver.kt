package pethersilva.com.notificationme.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import pethersilva.com.notificationme.R

class NotificationReceiver : BroadcastReceiver() {

    private val LOG_TAG: String = "NotificationReceiver"

    override fun onReceive(context: Context?, intent: Intent?) {
        //Code to update the notification
        Log.d(LOG_TAG, context?.resources?.getString(R.string.notification_receiver_message))
    }
}