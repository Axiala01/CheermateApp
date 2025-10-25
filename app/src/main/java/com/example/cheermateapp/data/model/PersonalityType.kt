package com.example.cheermateapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a personality type definition
 * This entity stores the available personality types that users can choose from
 */
@Entity(tableName = "PersonalityType")
data class PersonalityType(
    @PrimaryKey
    @ColumnInfo(name = "Type_ID")
    val Type_ID: Int,
    
    @ColumnInfo(name = "Name")
    val Name: String,
    
    @ColumnInfo(name = "Description")
    val Description: String,
    
    @ColumnInfo(name = "IsActive")
    val IsActive: Boolean = true,
    
    @ColumnInfo(name = "CreatedAt")
    val CreatedAt: Long = System.currentTimeMillis(),
    
    @ColumnInfo(name = "UpdatedAt")
    val UpdatedAt: Long = System.currentTimeMillis()
)
