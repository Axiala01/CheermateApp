package com.cheermateapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.util.Date
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Enum for reminder time options
 */
enum class ReminderType {
    TEN_MINUTES_BEFORE,
    THIRTY_MINUTES_BEFORE,
    AT_SPECIFIC_TIME
}

@Entity(
    tableName = "TaskReminder",
    primaryKeys = ["TaskReminder_ID", "Task_ID", "User_ID"],
    foreignKeys = [
        ForeignKey(
            entity = com.cheermateapp.data.model.Task::class,
            parentColumns = ["Task_ID", "User_ID"],
            childColumns = ["Task_ID", "User_ID"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("Task_ID"),
        Index("User_ID"),
        Index(value = ["Task_ID", "User_ID"])
    ]
)
data class TaskReminder(
    @ColumnInfo(name = "TaskReminder_ID")
    val TaskReminder_ID: Int = 0,
    
    @ColumnInfo(name = "Task_ID")
    val Task_ID: Int,
    
    @ColumnInfo(name = "User_ID")
    val User_ID: Int,
    
    @ColumnInfo(name = "RemindAt")
    val RemindAt: String,
    
    @ColumnInfo(name = "ReminderType")
    val ReminderType: ReminderType? = null,
    
    @ColumnInfo(name = "IsActive")
    val IsActive: Boolean = true,
    
    @ColumnInfo(name = "CreatedAt")
    val CreatedAt: String = TimestampUtil.getCurrentTimestamp(),
    
    @ColumnInfo(name = "UpdatedAt")
    val UpdatedAt: String = TimestampUtil.getCurrentTimestamp()
) {
    // Convert stored string back to timestamp for alarm scheduling
    val remindAtTimestamp: Long
        get() {
            return try {
                // Try to parse as readable format first
                val formatter = SimpleDateFormat("MMM dd, yyyy 'at' h:mm a", Locale.getDefault())
                val date = formatter.parse(RemindAt)
                date?.time ?: 0L
            } catch (e: Exception) {
                try {
                    // Fallback: try parsing as old timestamp format for backward compatibility
                    RemindAt.toLongOrNull() ?: 0L
                } catch (e2: Exception) {
                    0L
                }
            }
        }

    // Time only in 12-hour format (e.g., "2:30 PM")
    val formattedRemindAt: String
        get() {
            return try {
                val date = Date(remindAtTimestamp)
                val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
                formatter.format(date)
            } catch (e: Exception) {
                RemindAt // Return as-is if parsing fails
            }
        }

    // Full date and time in readable format (e.g., "Dec 14, 2025 at 2:30 PM")
    val formattedRemindAtFull: String
        get() = RemindAt // Already stored in human-readable format!

    // Short format with date and time (e.g., "12/14 at 2:30 PM")
    val formattedRemindAtShort: String
        get() {
            return try {
                val date = Date(remindAtTimestamp)
                val formatter = SimpleDateFormat("M/dd 'at' h:mm a", Locale.getDefault())
                formatter.format(date)
            } catch (e: Exception) {
                RemindAt
            }
        }

    // Date only (e.g., "Dec 14, 2025")
    val formattedRemindAtDate: String
        get() {
            return try {
                val date = Date(remindAtTimestamp)
                val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                formatter.format(date)
            } catch (e: Exception) {
                RemindAt.split(" at").firstOrNull() ?: RemindAt
            }
        }

    // Check if reminder is today
    val isToday: Boolean
        get() {
            return try {
                val reminderDate = java.util.Calendar.getInstance()
                reminderDate.timeInMillis = remindAtTimestamp
                
                val today = java.util.Calendar.getInstance()
                
                reminderDate.get(java.util.Calendar.YEAR) == today.get(java.util.Calendar.YEAR) &&
                        reminderDate.get(java.util.Calendar.DAY_OF_YEAR) == today.get(java.util.Calendar.DAY_OF_YEAR)
            } catch (e: Exception) {
                false
            }
        }

    // Smart formatting based on date proximity
    val formattedRemindAtSmart: String
        get() {
            return try {
                if (isToday) {
                    val date = Date(remindAtTimestamp)
                    val formatter = SimpleDateFormat("'Today at' h:mm a", Locale.getDefault())
                    formatter.format(date)
                } else {
                    RemindAt // Already in good format
                }
            } catch (e: Exception) {
                RemindAt
            }
        }

    companion object {
        // Helper function to format any timestamp to readable format
        fun formatTimestamp(timestampMillis: Long): String {
            val date = Date(timestampMillis)
            val formatter = SimpleDateFormat("MMM dd, yyyy 'at' h:mm a", Locale.getDefault())
            return formatter.format(date)
        }

        // Helper function to format timestamp with smart formatting
        fun formatTimestampSmart(timestampMillis: Long): String {
            val reminderDate = java.util.Calendar.getInstance()
            reminderDate.timeInMillis = timestampMillis
            
            val today = java.util.Calendar.getInstance()
            
            val isToday = reminderDate.get(java.util.Calendar.YEAR) == today.get(java.util.Calendar.YEAR) &&
                    reminderDate.get(java.util.Calendar.DAY_OF_YEAR) == today.get(java.util.Calendar.DAY_OF_YEAR)
            
            val date = Date(timestampMillis)
            return if (isToday) {
                val formatter = SimpleDateFormat("'Today at' h:mm a", Locale.getDefault())
                formatter.format(date)
            } else {
                val formatter = SimpleDateFormat("MMM dd 'at' h:mm a", Locale.getDefault())
                formatter.format(date)
            }
        }

        // Convert timestamp to human-readable string for storage
        fun timestampToReadableString(timestampMillis: Long): String {
            return formatTimestamp(timestampMillis)
        }

        // Parse human-readable string back to timestamp
        fun readableStringToTimestamp(readableString: String): Long {
            return try {
                val formatter = SimpleDateFormat("MMM dd, yyyy 'at' h:mm a", Locale.getDefault())
                val date = formatter.parse(readableString)
                date?.time ?: 0L
            } catch (e: Exception) {
                // Try parsing as old format (raw timestamp)
                try {
                    readableString.toLongOrNull() ?: 0L
                } catch (e2: Exception) {
                    0L
                }
            }
        }
    }

    // Override toString to show formatted time (now directly readable!)
    override fun toString(): String {
        return "TaskReminder(TaskReminder_ID=$TaskReminder_ID, Task_ID=$Task_ID, User_ID=$User_ID, " +
                "RemindAt='$RemindAt', ReminderType=$ReminderType, IsActive=$IsActive, " +
                "CreatedAt='$CreatedAt', UpdatedAt='$UpdatedAt')"
    }

    // Debug function that shows all formatted information
    fun toDebugString(): String {
        return """
        |📋 Reminder Details:
        |   ID: $TaskReminder_ID
        |   Task ID: $Task_ID  
        |   User ID: $User_ID
        |   🕐 Remind At: $formattedRemindAtFull
        |   📅 Smart Format: $formattedRemindAtSmart
        |   ⏰ Time Only: $formattedRemindAt
        |   🔔 Type: $ReminderType
        |   ✅ Active: $IsActive
        |   📝 Created: $CreatedAt
        |   🔄 Updated: $UpdatedAt
        """.trimMargin()
    }
}
