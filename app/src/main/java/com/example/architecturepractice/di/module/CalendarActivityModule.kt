package com.example.architecturepractice.di.module

import android.content.Context
import com.example.architecturepractice.calendar.CalendarActivity
import com.example.architecturepractice.calendar.CalendarFragment
import com.example.architecturepractice.calendar.MonthFragment
import com.example.architecturepractice.di.ActivityContext
import com.example.architecturepractice.di.FragmentScope
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class CalendarActivityModule {
    companion object {

        @Provides
        @ActivityContext
        fun provideContext(activity: CalendarActivity): Context {
            return activity
        }

    }

    @FragmentScope
    @ContributesAndroidInjector(modules = [CalendarModule::class])
    abstract fun getCalendarFragment(): CalendarFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [MonthModule::class])
    abstract fun getMonthFragment(): MonthFragment
}