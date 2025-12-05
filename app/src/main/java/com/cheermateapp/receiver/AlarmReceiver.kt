package com.cheermateapp.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.cheermateapp.CheermateApp
import com.cheermateapp.R
import com.cheermateapp.data.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getIntExtra("TASK_ID", -1)
        val userId = intent.getIntExtra("USER_ID", -1)
        if (taskId == -1 || userId == -1) return

        val db = (context.applicationContext as CheermateApp).db
        val taskRepository = TaskRepository(db.taskDao(), db.subTaskDao(), db.taskReminderDao(), db.taskDependencyDao())

        CoroutineScope(Dispatchers.IO).launch {
            val task = taskRepository.getTaskForReceiver(userId, taskId)
            task?.let {
                // You can also get the personalized message here
                val message = "Reminder for: ${it.Title}"
                showNotification(context, it.Task_ID, "Cheermate Reminder", message)
            }
        }
    }

    private fun showNotification(context: Context, taskId: Int, title: String, message: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "task_reminders" // Use existing channel from NotificationUtil

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_bell) // Replace with your notification icon
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(taskId, notification)
    }
}
