package com.example.todoapp.util

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.todoapp.R
import com.example.todoapp.view.MainActivity

class NotificationHelper(val context: Context) {
    private val CHANNEL_ID = "todo_channel_id"
    private val NOTIFICATION_ID = 1

    private fun createNotificationChannel(){
//        Checking OS Version because Oreo version and above requires channel
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_ID,
                NotificationManager.IMPORTANCE_DEFAULT).apply {
                    description = "ToDo channel description"
            }

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

    fun createNotification(title: String, message: String){
        createNotificationChannel()
        val intent = Intent(context, MainActivity::class.java).apply {
//            FLAG_ACTIVITY_NEW_TASK: If set, this activity will become the start of a new task ont his history stack
//            FLAG_ACTIVITY_CLEAR_TASK: This flag will cause any existing tasks that would be associated with the activity to be cleared before the activity is started
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        val icon = BitmapFactory.decodeResource(context.resources, R.drawable.todochar)

        val b: Bitmap? = null
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.checklist)
            .setLargeIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigLargeIcon(b)
                .bigPicture(icon)
            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }
}