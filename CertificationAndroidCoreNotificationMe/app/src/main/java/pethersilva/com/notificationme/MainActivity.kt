package pethersilva.com.notificationme

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*
import pethersilva.com.notificationme.receivers.NotificationReceiver

class MainActivity : AppCompatActivity() {

    private val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    private val NEWS_CHANNEL_ID = "news_notification_channel"
    private var mNotificationManager: NotificationManager? = null
    private val NOTIFICATION_ID = 0
    private val mNotificationIntent = Intent(Intent.ACTION_VIEW)
    private val ACTION_UPDATE_NOTIFICATION = "com.pethersilva.notificationme"
    private val mReceiver = NotificationReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(mReceiver, IntentFilter(ACTION_UPDATE_NOTIFICATION))

        setNotificationButtonState(isNotifyEnabled = true, isUpdateEnabled = false, isCancelEnabled = false)

        mNotificationIntent.data = Uri.parse("https://github.com/pethersilva")

        createNotificationChannel()

        btn_notify.setOnClickListener {
            sendNotification()
        }

        btn_update.setOnClickListener {
            updateNotification()
        }

        btn_cancel.setOnClickListener {
            cancelNotification()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }

    private fun updateNotification() {

        val androidImage = BitmapFactory.decodeResource(resources, R.drawable.mascot_1)
        val notificationBuilder = getNotificationBuilder()

        notificationBuilder.setStyle(NotificationCompat.BigPictureStyle()
            .bigPicture(androidImage)
            .setBigContentTitle(resources.getString(R.string.main_activity_notification_updated_message))
        )
        mNotificationManager?.notify(NOTIFICATION_ID, notificationBuilder.build())
        setNotificationButtonState(isNotifyEnabled = false, isUpdateEnabled = false, isCancelEnabled = true)
    }

    private fun cancelNotification() {
        mNotificationManager?.cancel(NOTIFICATION_ID)
        setNotificationButtonState(isNotifyEnabled = true, isUpdateEnabled = false, isCancelEnabled = false)
    }

    private fun sendNotification() {

        val updateIntent = Intent(ACTION_UPDATE_NOTIFICATION)
        val updatePendingIntent = PendingIntent.getBroadcast(this,
            NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT)
        val notificationBuilder = getNotificationBuilder()

        //R.drawable.ic_action_name only is showed in Nougat previous versions
        notificationBuilder.addAction(R.drawable.ic_action_name, resources.getString(R.string.main_activity_notification_action_message),
            updatePendingIntent)
        mNotificationManager?.notify(NOTIFICATION_ID, notificationBuilder.build())
        setNotificationButtonState(isNotifyEnabled = false, isUpdateEnabled = true, isCancelEnabled = true)
    }

    private fun createNotificationChannel() {
        mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =  NotificationChannel(PRIMARY_CHANNEL_ID, resources.getString(R.string.main_activity_primary_notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = resources.getString(R.string.main_activity_primary_notification_channel_description)

            val notificationChannelNews = NotificationChannel(NEWS_CHANNEL_ID, resources.getString(R.string.main_activity_news_notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH)
            notificationChannelNews.enableLights(true)
            notificationChannelNews.lightColor = Color.RED
            notificationChannelNews.enableVibration(true)
            notificationChannelNews.description = resources.getString(R.string.main_activity_news_notification_channel_description)

            mNotificationManager?.createNotificationChannel(notificationChannel)
            mNotificationManager?.createNotificationChannel(notificationChannelNews)
        }
    }

    private fun getNotificationBuilder(): NotificationCompat.Builder {

        val notificationPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, mNotificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID).
            setContentTitle(resources.getString(R.string.main_activity_notification_title))
            .setContentText(resources.getString(R.string.main_activity_notification_content_text))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setSmallIcon(R.drawable.ic_android).
            setContentIntent(notificationPendingIntent)
            .setAutoCancel(true)
    }

    private fun setNotificationButtonState(isNotifyEnabled: Boolean, isUpdateEnabled: Boolean, isCancelEnabled: Boolean) {
        btn_notify.isEnabled = isNotifyEnabled
        btn_update.isEnabled = isUpdateEnabled
        btn_cancel.isEnabled = isCancelEnabled
    }
}