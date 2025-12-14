package com.cheermateapp.util

import androidx.room.TypeConverter

class BooleanStringConverter {
    @TypeConverter
    fun fromBoolean(isCompleted: Boolean): String {
        return if (isCompleted) "Yes" else "No"
    }

    @TypeConverter
    fun toBoolean(isCompletedString: String): Boolean {
        return isCompletedString == "Yes"
    }
}