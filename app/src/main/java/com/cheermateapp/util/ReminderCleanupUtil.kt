package com.cheermateapp.util

import android.content.Context
import android.util.Log
import com.cheermateapp.data.db.AppDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Utility to clean up duplicate reminders in the database
 */
object ReminderCleanupUtil {
    
    private const val TAG = "ReminderCleanup"
    
    /**
     * Clean up duplicate reminders - keeps only the most recent reminder per task
     */
    suspend fun cleanupDuplicateReminders(context: Context) {
        withContext(Dispatchers.IO) {
            try {
                val db = AppDb.get(context)
                
                Log.d(TAG, "🧹 Starting cleanup of duplicate reminders...")
                
                // Get count before cleanup
                val allRemindersBefore = db.taskReminderDao().getAllReminders()
                Log.d(TAG, "📊 Found ${allRemindersBefore.size} total reminders before cleanup")
                
                // Show duplicates by task
                val duplicateGroups = allRemindersBefore.groupBy { "${it.Task_ID}-${it.User_ID}" }
                val duplicateTasks = duplicateGroups.filter { it.value.size > 1 }
                
                Log.d(TAG, "📋 Found ${duplicateTasks.size} tasks with duplicate reminders:")
                duplicateTasks.forEach { (taskKey, reminders) ->
                    Log.d(TAG, "  Task $taskKey: ${reminders.size} reminders")
                    reminders.forEach { reminder ->
                        Log.d(TAG, "    ID: ${reminder.TaskReminder_ID}, Type: ${reminder.ReminderType}, Time: ${reminder.formattedRemindAtFull}")
                    }
                }
                
                // Clean up duplicates (keeps only the latest one per task)
                db.taskReminderDao().cleanupDuplicateReminders()
                
                // Get count after cleanup
                val allRemindersAfter = db.taskReminderDao().getAllReminders()
                val deletedCount = allRemindersBefore.size - allRemindersAfter.size
                
                Log.d(TAG, "✅ Cleanup completed!")
                Log.d(TAG, "📊 Reminders after cleanup: ${allRemindersAfter.size}")
                Log.d(TAG, "🗑️ Deleted duplicate reminders: $deletedCount")
                
                if (deletedCount > 0) {
                    Log.d(TAG, "🎉 Successfully cleaned up $deletedCount duplicate reminder(s)!")
                } else {
                    Log.d(TAG, "✨ No duplicate reminders found - database is clean!")
                }
                
            } catch (e: Exception) {
                Log.e(TAG, "❌ Error during cleanup", e)
            }
        }
    }
    
    /**
     * Show current reminder statistics for debugging
     */
    suspend fun showReminderStats(context: Context) {
        withContext(Dispatchers.IO) {
            try {
                val db = AppDb.get(context)
                val allReminders = db.taskReminderDao().getAllReminders()
                
                Log.d(TAG, "📊 Reminder Statistics:")
                Log.d(TAG, "  Total reminders: ${allReminders.size}")
                
                val byTask = allReminders.groupBy { "${it.Task_ID}-${it.User_ID}" }
                val duplicates = byTask.filter { it.value.size > 1 }
                
                Log.d(TAG, "  Unique tasks with reminders: ${byTask.size}")
                Log.d(TAG, "  Tasks with duplicate reminders: ${duplicates.size}")
                
                if (duplicates.isNotEmpty()) {
                    Log.d(TAG, "  Duplicate tasks:")
                    duplicates.forEach { (taskKey, reminders) ->
                        Log.d(TAG, "    Task $taskKey: ${reminders.size} reminders")
                    }
                }
                
            } catch (e: Exception) {
                Log.e(TAG, "❌ Error getting stats", e)
            }
        }
    }
}