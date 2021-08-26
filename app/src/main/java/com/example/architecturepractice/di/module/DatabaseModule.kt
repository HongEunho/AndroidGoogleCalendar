package com.example.architecturepractice.di.module

import android.content.Context
import androidx.room.Room
import com.example.architecturepractice.data.database.CalendarDatabase
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDB(context: Context): CalendarDatabase {
        return Room.databaseBuilder(
            context, CalendarDatabase::class.java, CalendarDatabase.DB_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideIoDispatchers() = Dispatchers.IO
}