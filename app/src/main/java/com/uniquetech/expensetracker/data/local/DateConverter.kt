package com.uniquetech.expensetracker.data.local

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Room, LocalDateTime tipini doğrudan desteklemediği için 
 * veritabanında Unix timestamp olarak saklamak ve geriye 
 * dönüştürmek için TypeConverter sınıfı.
 */
class DateConverter {
    
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let { 
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(it), 
                ZoneId.systemDefault()
            ) 
        }
    }
    
    @TypeConverter
    fun toTimestamp(date: LocalDateTime?): Long? {
        return date?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
    }
} 