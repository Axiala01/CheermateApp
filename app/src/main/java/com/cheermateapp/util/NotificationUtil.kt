package com.cheermateapp.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.cheermateapp.MainActivity
import com.cheermateapp.R

/**
 * Utility class for managing task reminder notifications
 */
object NotificationUtil {
    
    private const val CHANNEL_ID = "task_reminders"
    private const val CHANNEL_NAME = "Task Reminders"
    private const val CHANNEL_DESCRIPTION = "Notifications for task reminders"
    
    /**
     * Create notification channel (required for Android O and above)
     */
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESCRIPTION
                enableLights(true)
                enableVibration(true)
                // Set the default alarm sound for the channel
                setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM), null)
                // Set a custom vibration pattern for the channel
                // Example: wait 0ms, vibrate 1000ms, pause 500ms, vibrate 1000ms
                vibrationPattern = longArrayOf(0, 1000, 500, 1000)
            }
            
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            
            android.util.Log.d("NotificationUtil", "✅ Notification channel created")
        }
    }
    
    /**
     * Show a task reminder notification
     */
    fun showTaskReminder(
        context: Context,
        taskId: Int,
        taskTitle: String,
        taskDescription: String?,
        userId: Int
    ) {
        // Create intent to open app when notification is clicked
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("USER_ID", userId)
            putExtra("TASK_ID", taskId)
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            taskId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Build notification
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification) // You'll need to add this icon
            .setContentTitle("Task Reminder: $taskTitle")
            .setContentText(taskDescription ?: "You have a task due soon")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setDefaults(NotificationCompat.DEFAULT_LIGHTS) // Enable default lights
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)) // Set alarm sound
            .setVibrate(longArrayOf(0, 1000, 500, 1000)) // Set custom vibration pattern
            .build()
        
        // Show notification
        try {
            NotificationManagerCompat.from(context).notify(taskId, notification)
            android.util.Log.d("NotificationUtil", "✅ Notification shown for task: $taskTitle")
        } catch (e: SecurityException) {
            android.util.Log.e("NotificationUtil", "Permission denied for notification", e)
        }
    }
    
    /**
     * Cancel a specific notification
     */
    fun cancelNotification(context: Context, notificationId: Int) {
        NotificationManagerCompat.from(context).cancel(notificationId)
    }
    
    /**
     * Check if notifications are enabled
     */
    fun areNotificationsEnabled(context: Context): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }
}
