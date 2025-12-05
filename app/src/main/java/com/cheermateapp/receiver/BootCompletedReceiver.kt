package com.cheermateapp.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.cheermateapp.CheermateApp
import com.cheermateapp.util.ReminderManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val db = (context.applicationContext as CheermateApp).db
            val reminderDao = db.taskReminderDao() // Assuming direct DAO access for simplicity here

            CoroutineScope(Dispatchers.IO).launch {
                val reminders = reminderDao.getAllActiveReminders() // Needs to be implemented
                reminders.forEach { reminder ->
                    val task = db.taskDao().getTaskByCompositeKey(reminder.User_ID, reminder.Task_ID)
                    task?.let {
                        ReminderManager.scheduleReminder(
                            context,
                            it.Task_ID,
                            it.Title,
                            it.Description,
                            it.User_ID,
                            reminder.RemindAt
                        )
                    }
                }
            }
        }
    }
}
