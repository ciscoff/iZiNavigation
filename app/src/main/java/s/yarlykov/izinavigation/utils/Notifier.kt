package s.yarlykov.izinavigation.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import s.yarlykov.izinavigation.R
import javax.inject.Inject

/**
 * Utility class for posting notifications.
 * This class creates the notification channel (as necessary) and posts to it when requested.
 */
class Notifier @Inject constructor(var context: Context) {

    companion object {
        private const val channelId = "Default"
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager

            val existingChannel = notificationManager.getNotificationChannel(channelId)

            if (existingChannel == null) {
                val name = context.getString(R.string.default_channel)
                val importance = NotificationManager.IMPORTANCE_DEFAULT
                val mChannel = NotificationChannel(channelId, name, importance)
                mChannel.description = context.getString(R.string.notification_description)
                notificationManager.createNotificationChannel(mChannel)
            }
        }
    }

    fun postNotification(id: Int, hint: Int, context: Context, intent: PendingIntent) {

        /**
         * Выбрать инконку
         */
        val drawableId = when(id) {
            R.id.navigation_home -> R.drawable.ic_home_blue
            R.id.navigation_dashboard -> R.drawable.ic_dashboard_blue
            R.id.navigation_notifications -> R.drawable.ic_notifications_blue
            else -> R.drawable.ic_android
        }

        val builder = NotificationCompat.Builder(context, channelId)
        builder.setContentTitle(context.getString(R.string.deepLink_notification_title))
            .setSmallIcon(drawableId)
        val text = context.getString(hint)

        val notification = builder.setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(intent)
            .setAutoCancel(true)
            .build()
        val notificationManager = NotificationManagerCompat.from(context)

        // Remove prior notifications; only allow one at a time to edit the latest item
        notificationManager.cancelAll()
        notificationManager.notify(id, notification)
    }
}