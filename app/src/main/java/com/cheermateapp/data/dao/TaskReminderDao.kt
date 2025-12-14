package com.cheermateapp.data.dao

import androidx.room.*
import com.cheermateapp.data.model.TaskReminder
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskReminderDao {

    @Insert
    suspend fun insert(rem: TaskReminder): Long

    @Update
    suspend fun update(reminder: TaskReminder)

    @Delete
    suspend fun delete(reminder: TaskReminder)

    @Query("SELECT * FROM TaskReminder")
    suspend fun getAllReminders(): List<TaskReminder>

    @Query("SELECT * FROM TaskReminder WHERE Task_ID = :taskId AND User_ID = :userId AND IsActive = 1 ORDER BY RemindAt ASC")
    suspend fun activeForTask(taskId: Int, userId: Int): List<TaskReminder>

    @Query("SELECT * FROM TaskReminder WHERE Task_ID = :taskId")
    suspend fun getRemindersByTask(taskId: Int): List<TaskReminder>

    // ✅ FLOW METHODS FOR REACTIVE UPDATES
    @Query("SELECT * FROM TaskReminder WHERE Task_ID = :taskId AND User_ID = :userId AND IsActive = 1 ORDER BY RemindAt ASC")
    fun getActiveRemindersFlow(taskId: Int, userId: Int): Flow<List<TaskReminder>>

    @Query("SELECT * FROM TaskReminder WHERE Task_ID = :taskId")
    fun getRemindersByTaskFlow(taskId: Int): Flow<List<TaskReminder>>

    // ✅ GET NEXT REMINDER ID FOR USER
    @Query("SELECT COALESCE(MAX(TaskReminder_ID), 0) + 1 FROM TaskReminder WHERE User_ID = :userId")
    suspend fun getNextReminderIdForUser(userId: Int): Int

    // ✅ BATCH OPERATIONS WITH @Transaction
    @Transaction
    @Insert
    suspend fun insertAll(reminders: List<TaskReminder>)

    @Transaction
    @Query("DELETE FROM TaskReminder WHERE Task_ID = :taskId AND User_ID = :userId")
    suspend fun deleteAllForTask(taskId: Int, userId: Int)

    @Query("SELECT * FROM TaskReminder WHERE IsActive = 1")
    suspend fun getAllActiveReminders(): List<TaskReminder>

    // ✅ UTILITY: Clean up duplicate reminders (keeps only the latest one per task)
    @Query("""
        DELETE FROM TaskReminder 
        WHERE TaskReminder_ID NOT IN (
            SELECT MAX(TaskReminder_ID) 
            FROM TaskReminder 
            GROUP BY Task_ID, User_ID
        )
    """)
    suspend fun cleanupDuplicateReminders()
}
