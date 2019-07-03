package com.pethersilva.certificationandroidcoreservices.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.Random

class RandomNumberService : Service() {

    private val binder = RandomNumberServiceBinder()
    private val mGenerator = Random()
    val randomNumber: Int get() = mGenerator.nextInt(100)

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    inner class RandomNumberServiceBinder : Binder() {
        fun getService(): RandomNumberService = this@RandomNumberService
    }
}