package com.example.cheermateapp.data.dao

import androidx.room.*
import com.example.cheermateapp.data.model.PersonalityType

@Dao
interface PersonalityTypeDao {
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(personalityType: PersonalityType): Long
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(personalityTypes: List<PersonalityType>)
    
    @Update
    suspend fun update(personalityType: PersonalityType)
    
    @Delete
    suspend fun delete(personalityType: PersonalityType)
    
    @Query("SELECT * FROM PersonalityType WHERE IsActive = 1 ORDER BY Type_ID")
    suspend fun getAllActive(): List<PersonalityType>
    
    @Query("SELECT * FROM PersonalityType ORDER BY Type_ID")
    suspend fun getAll(): List<PersonalityType>
    
    @Query("SELECT * FROM PersonalityType WHERE Type_ID = :typeId")
    suspend fun getById(typeId: Int): PersonalityType?
    
    @Query("SELECT * FROM PersonalityType WHERE Name = :name")
    suspend fun getByName(name: String): PersonalityType?
    
    @Query("SELECT COUNT(*) FROM PersonalityType")
    suspend fun getCount(): Int
    
    @Query("SELECT MAX(UpdatedAt) FROM PersonalityType")
    suspend fun getLastModifiedTimestamp(): Long?
}
