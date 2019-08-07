package pethersilva.com.certificationandroidcorebroadcastreceiver

import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.buttonSendBroadcast
import pethersilva.com.certificationandroidcorebroadcastreceiver.receivers.CustomReceiver

class MainActivity : AppCompatActivity() {

    private val mReceiver : CustomReceiver = CustomReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intentFilter : IntentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED)
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)

        registerReceiver(mReceiver, intentFilter)

        buttonSendBroadcast.setOnClickListener {

        }
    }

    override fun onDestroy() {
        unregisterReceiver(mReceiver)
        super.onDestroy()
    }
}