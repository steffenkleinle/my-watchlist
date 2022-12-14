package app.mywatchlist.utils

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import app.mywatchlist.MainActivity
import app.mywatchlist.R
import java.time.ZonedDateTime

private const val CHANNEL_ID = "default"
private const val CHANNEL_NAME = "Default"

private const val NOTIFICATION_TITLE = "Today's Trending Movies"
private const val NOTIFICATION_TEXT = "Click on the notification to see today's trending movies!"

fun notify(context: Context, title: String = NOTIFICATION_TITLE, text: String = NOTIFICATION_TEXT) {
    val notificationPressedIntent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent: PendingIntent =
        PendingIntent.getActivity(
            context,
            0,
            notificationPressedIntent,
            PendingIntent.FLAG_IMMUTABLE
        )


    val channel = NotificationChannel(
        CHANNEL_ID,
        CHANNEL_NAME,
        NotificationManager.IMPORTANCE_DEFAULT
    ).apply { description = CHANNEL_NAME }
    val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        // TODO Use app icon
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(title)
        .setContentText(text)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)


    with(NotificationManagerCompat.from(context)) {
        notify(System.currentTimeMillis().toInt(), builder.build())
    }
}

fun schedulePushNotifications(context: Context) {
    val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
    val alarmPendingIntent by lazy {
        val intent = Intent(context, AlarmReceiver::class.java)
        PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
    }

    val scheduledTime = ZonedDateTime.now().plusDays(1).toInstant().toEpochMilli()

    alarmManager.setExact(
        AlarmManager.RTC_WAKEUP,
        scheduledTime,
        alarmPendingIntent
    )
}

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            schedulePushNotifications(context)
        }
    }
}

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        notify(context, NOTIFICATION_TITLE, NOTIFICATION_TEXT)
        schedulePushNotifications(context)
    }
}