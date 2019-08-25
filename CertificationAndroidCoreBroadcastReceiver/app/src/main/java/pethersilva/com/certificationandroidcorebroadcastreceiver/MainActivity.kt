package pethersilva.com.certificationandroidcorebroadcastreceiver

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.activity_main.buttonSendBroadcast
import pethersilva.com.certificationandroidcorebroadcastreceiver.receivers.CustomReceiver

class MainActivity : AppCompatActivity() {

    private val mReceiver : CustomReceiver = CustomReceiver()

    companion object {
        const  val ACTION_CUSTOM_BROADCAST = "${BuildConfig.APPLICATION_ID}." +
                "ACTION_CUSTOM_BROADCAST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED)
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)

        registerReceiver(mReceiver, intentFilter)

        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver, IntentFilter(
            ACTION_CUSTOM_BROADCAST)
        )

        buttonSendBroadcast.setOnClickListener {
            val customBroadcastIntent = Intent(ACTION_CUSTOM_BROADCAST)
            LocalBroadcastManager.getInstance(this).sendBroadcast(customBroadcastIntent)
        }
    }

    override fun onDestroy() {
        unregisterReceiver(mReceiver)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver)
        super.onDestroy()
    }
}