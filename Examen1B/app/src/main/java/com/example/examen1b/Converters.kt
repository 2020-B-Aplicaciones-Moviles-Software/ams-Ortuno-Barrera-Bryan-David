package com.example.examen1b

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value:String): Date? {
        return SimpleDateFormat("dd/MM/yyyy").parse(value)
    }


    @TypeConverter
    fun dateToTimestamp(date: Date): String? {
        return SimpleDateFormat("dd/MM/yyyy").format(date)
    }
}