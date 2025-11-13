package com.example.cheermateapp.util

import android.content.Context
import android.util.Log
import com.example.cheermateapp.data.db.AppDb
import com.example.cheermateapp.data.model.MessageTemplate
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
     * Seeds the database with default message templates for each personality type
     */
    suspend fun seedMessageTemplates(context: Context) {
        withContext(Dispatchers.IO) {
            try {
                val db = AppDb.get(context)
                val count = db.messageTemplateDao().getCount()
                
                if (count == 0) {
                    Log.d(TAG, "Seeding message templates...")
                    val messageTemplates = listOf(
                        // Kalog Vibes (Type_ID = 1) - Funny and entertaining
                        MessageTemplate(
                            Personality_ID = 1,
                            Category = "task_created",
                            TextTemplate = "Hala sige! Let's get this party started! 🎉"
                        ),
                        MessageTemplate(
                            Personality_ID = 1,
                            Category = "task_completed",
                            TextTemplate = "Yown! Tapos na yan! Bili tayo ice cream to celebrate! 🍦"
                        ),
                        MessageTemplate(
                            Personality_ID = 1,
                            Category = "motivation",
                            TextTemplate = "Kaya mo yan, besh! Let's go, walang iwanan! 💪"
                        ),
                        MessageTemplate(
                            Personality_ID = 1,
                            Category = "reminder",
                            TextTemplate = "Hoy! May task ka pa dyan oh! 'Wag mo akong i-ghost! 😂"
                        ),
                        
                        // GenZ Conyo (Type_ID = 2) - Rich tita energy with modern twist
                        MessageTemplate(
                            Personality_ID = 2,
                            Category = "task_created",
                            TextTemplate = "OMG! Ganern?! Let's do this, babe! ✨"
                        ),
                        MessageTemplate(
                            Personality_ID = 2,
                            Category = "task_completed",
                            TextTemplate = "Yayyy! So proud of you, babe! You're lit AF! 💅"
                        ),
                        MessageTemplate(
                            Personality_ID = 2,
                            Category = "motivation",
                            TextTemplate = "Slay mo 'yan, babe! You're doing amazing, sweetie! 💖"
                        ),
                        MessageTemplate(
                            Personality_ID = 2,
                            Category = "reminder",
                            TextTemplate = "Babe naman! Don't forget your task ha! Lowkey need to do it na! ⏰"
                        ),
                        
                        // Softy Bebe (Type_ID = 3) - Gentle, caring companion
                        MessageTemplate(
                            Personality_ID = 3,
                            Category = "task_created",
                            TextTemplate = "I believe in you! You can do this! 🌸"
                        ),
                        MessageTemplate(
                            Personality_ID = 3,
                            Category = "task_completed",
                            TextTemplate = "I'm so proud of you! You did an amazing job! 💕"
                        ),
                        MessageTemplate(
                            Personality_ID = 3,
                            Category = "motivation",
                            TextTemplate = "Take your time, you're doing wonderful! I'm here for you! 🤗"
                        ),
                        MessageTemplate(
                            Personality_ID = 3,
                            Category = "reminder",
                            TextTemplate = "Gentle reminder: You have a task waiting. I know you can do it! 💫"
                        ),
                        
                        // Mr. Grey (Type_ID = 4) - Professional, direct, mysteriously motivating
                        MessageTemplate(
                            Personality_ID = 4,
                            Category = "task_created",
                            TextTemplate = "Task acknowledged. Let's proceed efficiently."
                        ),
                        MessageTemplate(
                            Personality_ID = 4,
                            Category = "task_completed",
                            TextTemplate = "Excellent work. Your discipline is commendable."
                        ),
                        MessageTemplate(
                            Personality_ID = 4,
                            Category = "motivation",
                            TextTemplate = "Focus. Execute. Succeed. You have what it takes."
                        ),
                        MessageTemplate(
                            Personality_ID = 4,
                            Category = "reminder",
                            TextTemplate = "Your task requires attention. Time is valuable."
                        ),
                        
                        // Flirty Vibes (Type_ID = 5) - Playful, charming, smoothly motivating
                        MessageTemplate(
                            Personality_ID = 5,
                            Category = "task_created",
                            TextTemplate = "Ooh, a new challenge? I love watching you work! 😉"
                        ),
                        MessageTemplate(
                            Personality_ID = 5,
                            Category = "task_completed",
                            TextTemplate = "Look at you go! You're absolutely crushing it! 😍"
                        ),
                        MessageTemplate(
                            Personality_ID = 5,
                            Category = "motivation",
                            TextTemplate = "You're doing amazing, gorgeous! Keep that energy up! 💋"
                        ),
                        MessageTemplate(
                            Personality_ID = 5,
                            Category = "reminder",
                            TextTemplate = "Hey there, handsome/beautiful! Don't forget about that task! 😘"
                        )
                    )
                    
                    db.messageTemplateDao().insertAll(messageTemplates)
                    Log.d(TAG, "Successfully seeded ${messageTemplates.size} message templates")
                } else {
                    Log.d(TAG, "Message templates already seeded ($count records)")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error seeding message templates", e)
            }
        }
    }
    
    /**
     * Seeds all default data
     */
    suspend fun seedAll(context: Context) {
        seedPersonalityTypes(context)
        seedSecurityQuestions(context)
        seedMessageTemplates(context)
    }
}
