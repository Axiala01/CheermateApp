package com.cheermateapp.data.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

// ✅ MAKE SURE THESE ENUMS ARE AT THE TOP AND PROPERLY DEFINED
enum class Priority {
    Low,
    Medium,
    High
}

enum class Status {
    Pending,
    @SerializedName("In Progress")
    InProgress,
    Completed,
    Cancelled,
    OverDue
}

enum class Category {
    Work,
    Personal,
    Shopping,
    Others
}

// Extension function for Category display text with icons
fun Category.getDisplayText(): String {
    return when (this) {
        Category.Work -> "📋 Work"
        Category.Personal -> "👤 Personal"
        Category.Shopping -> "🛒 Shopping"
        Category.Others -> "📌 Others"
    }
}

@Entity(
    tableName = "Task",
    primaryKeys = ["Task_ID", "User_ID"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["User_ID"],
            childColumns = ["User_ID"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("User_ID")]
)
data class Task(
    @ColumnInfo(name = "Task_ID")
    val Task_ID: Int = 0,

    @ColumnInfo(name = "User_ID")
    val User_ID: Int,

    @ColumnInfo(name = "Title")
    val Title: String,

    @ColumnInfo(name = "Description")
    val Description: String? = null,

    // ✅ Category field
    @ColumnInfo(name = "Category")
    val Category: com.cheermateapp.data.model.Category = com.cheermateapp.data.model.Category.Work,

    // ✅ LINE 43: This should now work with the enum defined above
    @ColumnInfo(name = "Priority")
    val Priority: com.cheermateapp.data.model.Priority = com.cheermateapp.data.model.Priority.Medium,

    @ColumnInfo(name = "DueDate")
    val DueDate: String? = null,

    @ColumnInfo(name = "DueTime")
    val DueTime: String? = null,

    @ColumnInfo(name = "Status")
    val Status: com.cheermateapp.data.model.Status = com.cheermateapp.data.model.Status.Pending,

    @ColumnInfo(name = "TaskProgress")
    val TaskProgress: Int = 0,

    @ColumnInfo(name = "CreatedAt")
    val CreatedAt: String = TimestampUtil.getCurrentTimestamp(),

    @ColumnInfo(name = "UpdatedAt")
    val UpdatedAt: String = TimestampUtil.getCurrentTimestamp()
) {
    companion object {
        fun create(
            taskId: Int = 0,
            userId: Int,
            title: String,
            description: String? = null,
            category: com.cheermateapp.data.model.Category = com.cheermateapp.data.model.Category.Work,
            priority: com.cheermateapp.data.model.Priority = com.cheermateapp.data.model.Priority.Medium,
            dueDate: String? = null,
            dueTime: String? = null,
            status: com.cheermateapp.data.model.Status = com.cheermateapp.data.model.Status.Pending,
            taskProgress: Int = 0,
            createdAt: String = TimestampUtil.getCurrentTimestamp(),
            updatedAt: String = TimestampUtil.getCurrentTimestamp()
        ) = Task(
            Task_ID = taskId,
            User_ID = userId,
            Title = title,
            Description = description,
            Category = category,
            Priority = priority,
            DueDate = dueDate,
            DueTime = dueTime,
            Status = status,
            TaskProgress = taskProgress,
            CreatedAt = createdAt,
            UpdatedAt = updatedAt
        )

        fun dateToString(date: Date): String {
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            return format.format(date)
        }

        fun timeToString(date: Date): String {
            val format = SimpleDateFormat("h:mm a", Locale.getDefault())
            return format.format(date)
        }

        fun dateToTimeString(date: Date): String {
            val format = SimpleDateFormat("h:mm a", Locale.getDefault())
            return format.format(date)
        }

        fun stringToDate(dateStr: String, timeStr: String?): Date? {
            val dateTimeStr = if (timeStr != null) {
                "$dateStr $timeStr"
            } else {
                "$dateStr 12:00 AM"
            }
            
            return try {
                // Try 12-hour format first (h:mm a)
                val format12Hour = SimpleDateFormat("yyyy-MM-dd h:mm a", Locale.getDefault())
                format12Hour.parse(dateTimeStr)
            } catch (e: Exception) {
                try {
                    // Try 24-hour format for backward compatibility
                    val format24Hour = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                    format24Hour.parse(dateTimeStr)
                } catch (e2: Exception) {
                    try {
                        // Try legacy format with seconds
                        val formatLegacy = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                        formatLegacy.parse(dateTimeStr)
                    } catch (e3: Exception) {
                        try {
                            // Try another legacy format
                            val formatOldLegacy = SimpleDateFormat("MMM dd, yyyy HH:mm:ss", Locale.getDefault())
                            formatOldLegacy.parse(dateTimeStr)
                        } catch (e4: Exception) {
                            // All formats failed
                            null
                        }
                    }
                }
            }
        }
    }

    fun getParsedDueDate(): Date? {
        return if (DueDate != null) {
            Task.stringToDate(DueDate, DueTime)
        } else null
    }

    fun isOverdue(): Boolean {
        val dueDate = getParsedDueDate()
        return dueDate != null && dueDate.before(Date()) && Status != com.cheermateapp.data.model.Status.Completed
    }

    fun isToday(): Boolean {
        val dueDate = getParsedDueDate()
        if (dueDate == null) return false

        val today = Calendar.getInstance()
        val dueCalendar = Calendar.getInstance()
        dueCalendar.time = dueDate

        return today.get(Calendar.YEAR) == dueCalendar.get(Calendar.YEAR) &&
                today.get(Calendar.DAY_OF_YEAR) == dueCalendar.get(Calendar.DAY_OF_YEAR)
    }

    fun getFormattedDueDateTime(): String? {
        return if (DueDate != null) {
            val dueDate = getParsedDueDate()
            if (dueDate != null) {
                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
                val formattedDate = dateFormat.format(dueDate)
                val formattedTime = if (DueTime != null) {
                    " at ${timeFormat.format(dueDate)}"
                } else ""
                "$formattedDate$formattedTime"
            } else null
        } else null
    }

    fun getStatusEmoji(): String {
        return when (Status) {
            com.cheermateapp.data.model.Status.Completed -> "✅"
            com.cheermateapp.data.model.Status.Pending -> "⏳"
            com.cheermateapp.data.model.Status.InProgress -> "🔄"
            com.cheermateapp.data.model.Status.Cancelled -> "❌"
            com.cheermateapp.data.model.Status.OverDue -> "🔴"
            else -> "⏳" // Default fallback
        }
    }

    fun com.cheermateapp.data.model.Task.getPriorityColor(): Int {
        return when (Priority) {
            com.cheermateapp.data.model.Priority.High -> 0xFFE53E3E.toInt() // Red
            com.cheermateapp.data.model.Priority.Medium -> 0xFFED8936.toInt() // Orange
            com.cheermateapp.data.model.Priority.Low -> 0xFF38A169.toInt() // Green
            else -> 0xFFED8936.toInt() // Default to Medium Orange
        }
    }
    fun getStatusColor(): Int {
        return when (Status) {
            com.cheermateapp.data.model.Status.Pending -> 0xFFFFA500.toInt()  // Orange
            com.cheermateapp.data.model.Status.InProgress -> 0xFF0066CC.toInt() // Blue
            com.cheermateapp.data.model.Status.Completed -> 0xFF38A169.toInt()  // Green
            com.cheermateapp.data.model.Status.Cancelled -> 0xFF808080.toInt()  // Gray
            com.cheermateapp.data.model.Status.OverDue -> 0xFFE53E3E.toInt()    // Red
        }
    }

    fun getPriorityText(): String {
        return when (Priority) {
            com.cheermateapp.data.model.Priority.High -> "🔴 High"
            com.cheermateapp.data.model.Priority.Medium -> "🟡 Medium"
            com.cheermateapp.data.model.Priority.Low -> "🟢 Low"
        }
    }

    fun getStatusText(): String {
        return when (Status) {
            com.cheermateapp.data.model.Status.Pending -> "⏳ Pending"
            com.cheermateapp.data.model.Status.InProgress -> "🔄 In Progress"
            com.cheermateapp.data.model.Status.Completed -> "✅ Done"
            com.cheermateapp.data.model.Status.Cancelled -> "❌ Cancelled"
            com.cheermateapp.data.model.Status.OverDue -> "🔴 Overdue"
        }
    }
}
