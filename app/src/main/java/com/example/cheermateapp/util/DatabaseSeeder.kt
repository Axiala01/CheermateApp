package com.example.cheermateapp.util

import android.content.Context
import android.util.Log
import com.example.cheermateapp.data.db.AppDb
import com.example.cheermateapp.data.model.PersonalityType
import com.example.cheermateapp.data.model.SecurityQuestion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Utility class for seeding the database with default static data
 */
object DatabaseSeeder {
    
    private const val TAG = "DatabaseSeeder"
    
    /**
     * Seeds the database with default personality types if not already present
     */
    suspend fun seedPersonalityTypes(context: Context) {
        withContext(Dispatchers.IO) {
            try {
                val db = AppDb.get(context)
                val count = db.personalityTypeDao().getCount()
                
                if (count == 0) {
                    Log.d(TAG, "Seeding personality types...")
                    val personalityTypes = listOf(
                        PersonalityType(
                            Type_ID = 1,
                            Name = "Kalog",
                            Description = "The funny friend who makes everything entertaining!",
                            IsActive = true
                        ),
                        PersonalityType(
                            Type_ID = 2,
                            Name = "Gen Z",
                            Description = "Tech-savvy and trendy with the latest slang!",
                            IsActive = true
                        ),
                        PersonalityType(
                            Type_ID = 3,
                            Name = "Softy",
                            Description = "Gentle and caring with a warm heart!",
                            IsActive = true
                        ),
                        PersonalityType(
                            Type_ID = 4,
                            Name = "Grey",
                            Description = "Calm and balanced with steady wisdom!",
                            IsActive = true
                        ),
                        PersonalityType(
                            Type_ID = 5,
                            Name = "Flirty",
                            Description = "Playful and charming with a wink!",
                            IsActive = true
                        )
                    )
                    
                    db.personalityTypeDao().insertAll(personalityTypes)
                    Log.d(TAG, "Successfully seeded ${personalityTypes.size} personality types")
                } else {
                    Log.d(TAG, "Personality types already seeded ($count records)")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error seeding personality types", e)
            }
        }
    }
    
    /**
     * Seeds the database with default security questions if not already present
     */
    suspend fun seedSecurityQuestions(context: Context) {
        withContext(Dispatchers.IO) {
            try {
                val db = AppDb.get(context)
                val count = db.securityDao().getQuestionCount()
                
                if (count == 0) {
                    Log.d(TAG, "Seeding security questions...")
                    val questions = listOf(
                        SecurityQuestion(
                            Prompt = "What was your first pet's name?",
                            IsActive = true
                        ),
                        SecurityQuestion(
                            Prompt = "What city were you born in?",
                            IsActive = true
                        ),
                        SecurityQuestion(
                            Prompt = "What is your mother's maiden name?",
                            IsActive = true
                        ),
                        SecurityQuestion(
                            Prompt = "What was your first car?",
                            IsActive = true
                        ),
                        SecurityQuestion(
                            Prompt = "What elementary school did you attend?",
                            IsActive = true
                        ),
                        SecurityQuestion(
                            Prompt = "What is your favorite color?",
                            IsActive = true
                        ),
                        SecurityQuestion(
                            Prompt = "What was the name of your first boss?",
                            IsActive = true
                        ),
                        SecurityQuestion(
                            Prompt = "In what city did you meet your spouse/significant other?",
                            IsActive = true
                        )
                    )
                    
                    db.securityDao().insertAllQuestions(questions)
                    Log.d(TAG, "Successfully seeded ${questions.size} security questions")
                } else {
                    Log.d(TAG, "Security questions already seeded ($count records)")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error seeding security questions", e)
            }
        }
    }
    
    /**
     * Seeds all default data
     */
    suspend fun seedAll(context: Context) {
        seedPersonalityTypes(context)
        seedSecurityQuestions(context)
    }
}
