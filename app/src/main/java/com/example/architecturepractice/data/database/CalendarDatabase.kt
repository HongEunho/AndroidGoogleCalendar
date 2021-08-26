package com.example.architecturepractice.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.architecturepractice.data.database.dao.CalendarDao
import com.example.architecturepractice.data.database.dao.EventDao
import com.example.architecturepractice.data.entity.CalendarEntity
import com.example.architecturepractice.data.entity.EventEntity
import com.example.architecturepractice.data.entity.MyConverter

@Database(entities = [CalendarEntity::class, EventEntity::class], version = 1, exportSchema = false)
@TypeConverters(MyConverter::class)
abstract class CalendarDatabase: RoomDatabase() {
    abstract fun calendarDao(): CalendarDao
    abstract fun eventDao(): EventDao

    companion object {
        const val DB_NAME = "CalendarDB.db"
    }
}