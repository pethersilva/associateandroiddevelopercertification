package pethersilva.com.certificationandroidcorebroadcastreceiver.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import pethersilva.com.certificationandroidcorebroadcastreceiver.MainActivity

class CustomReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val intentAction : String? = intent.action
        var toastMessage = "unknown intent action"

        if (intentAction != null) {

            when (intentAction) {
                Intent.ACTION_POWER_DISCONNECTED -> {
                    toastMessage = "Power disconnected"
                }

                Intent.ACTION_POWER_CONNECTED -> {
                    toastMessage = "Power connected"
                }

                MainActivity.ACTION_CUSTOM_BROADCAST -> {
                    toastMessage = "Action Custom Broadcast Received"
                }
            }
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        }
    }
}