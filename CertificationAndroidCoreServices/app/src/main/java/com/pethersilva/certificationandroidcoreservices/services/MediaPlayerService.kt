package com.pethersilva.certificationandroidcoreservices.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast
import com.pethersilva.certificationandroidcoreservices.R

class MediaPlayerService : Service() {

    private var mediaPlayer : MediaPlayer? = null

    override fun onCreate() {

        Toast.makeText(this, resources.getString(R.string.service_created_message), Toast.LENGTH_LONG).show()
        mediaPlayer = MediaPlayer.create(this, R.raw.zelda)
        mediaPlayer?.isLooping = false
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaPlayer?.start()
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        mediaPlayer?.stop()
    }
}