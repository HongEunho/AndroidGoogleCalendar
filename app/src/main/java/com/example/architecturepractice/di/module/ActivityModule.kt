package com.example.architecturepractice.di.module

import com.example.architecturepractice.calendar.CalendarActivity
import com.example.architecturepractice.di.ActivityScope
import com.example.architecturepractice.signin.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [LoginActivityModule::class])
    abstract fun loginActivity(): LoginActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [CalendarActivityModule::class])
    abstract fun calendarActivity(): CalendarActivity

}