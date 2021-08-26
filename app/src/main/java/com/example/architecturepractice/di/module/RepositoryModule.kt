package com.example.architecturepractice.di.module

import com.example.architecturepractice.data.CalendarDataSource
import com.example.architecturepractice.data.CalendarRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
public abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindCalendarRepository(repository: CalendarRepository): CalendarDataSource

}