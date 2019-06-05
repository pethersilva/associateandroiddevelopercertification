package pethersilva.com.certificationandroidcorejobscheduler.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import pethersilva.com.certificationandroidcorejobscheduler.MainActivity
import pethersilva.com.certificationandroidcorejobscheduler.R

class NotificationJobService : JobService() {

    private var mNotificationManager: NotificationManager? = null
    private val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    private val NOTIFICATION_ID = 0

    override fun onStartJob(p0: JobParameters?): Boolean {
        createNotificationChannel()

        val pendingIntent = PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
            .setContentTitle(resources.getString(R.string.main_activity_job_title))
            .setContentText(resources.getString(R.string.main_activity_job_content_text))
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_job_running)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
        mNotificationManager?.notify(NOTIFICATION_ID, notification.build())
        return false
    }

    override fun onStopJob(p0: JobParameters?): Boolean {
        return true
    }

    private fun createNotificationChannel() {
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID, resources.getString(R.string.job_schedule_notification_channel),
                NotificationManager.IMPORTANCE_HIGH)

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = resources.getString(R.string.job_schedule_notification_description)
            mNotificationManager?.createNotificationChannel(notificationChannel)
        }
    }
}