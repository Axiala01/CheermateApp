package com.cheermateapp.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.cheermateapp.data.model.Task
import com.cheermateapp.data.model.TaskReminder
import com.cheermateapp.receiver.AlarmReceiver

object AlarmScheduler {

    fun schedule(context: Context, task: Task, reminder: TaskReminder) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("TASK_ID", task.Task_ID)
            putExtra("USER_ID", task.User_ID)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.TaskReminder_ID, // Use a unique ID for the reminder
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Check for exact alarm permission
        if (alarmManager.canScheduleExactAlarms()) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                reminder.RemindAt,
                pendingIntent
            )
        }
    }

    fun cancel(context: Context, reminder: TaskReminder) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.TaskReminder_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
}
