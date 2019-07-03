package com.pethersilva.certificationandroidcoreservices

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pethersilva.certificationandroidcoreservices.services.MediaPlayerService
import com.pethersilva.certificationandroidcoreservices.services.RandomNumberService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mService : RandomNumberService
    private var mIsBounded : Boolean = false
    private val connection = object : ServiceConnection {

        override fun onServiceDisconnected(name: ComponentName?) {
            mIsBounded = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as RandomNumberService.RandomNumberServiceBinder
            mService = binder.getService()
            mIsBounded = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startService(Intent(this, RandomNumberService::class.java))

        buttonStartService.setOnClickListener {
            startService(Intent(this, MediaPlayerService::class.java))
        }

        buttonStopService.setOnClickListener {
            stopService(Intent(this, MediaPlayerService::class.java))
        }

        buttonRandomNumber.setOnClickListener {
            if (mIsBounded) {
                val num: Int = mService.randomNumber
                Toast.makeText(this, "number: $num", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Intent(this, RandomNumberService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mIsBounded = false
    }
}