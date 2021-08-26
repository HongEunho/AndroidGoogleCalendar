package com.example.architecturepractice.di

import android.content.Context
import com.example.architecturepractice.TestApplication
import com.example.architecturepractice.data.CalendarDaoTest
import com.example.architecturepractice.di.component.ApplicationComponent
import com.example.architecturepractice.di.module.PreferenceModule
import com.example.architecturepractice.viewmodel.CalendarViewModelTest
import com.example.architecturepractice.viewmodel.MonthViewModelTest
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, TestApplicationModule::class, PreferenceModule::class])
interface TestApplicationComponent: ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): TestApplicationComponent
    }

    fun inject(app: TestApplication)
    fun inject(calendarViewModelTest: CalendarViewModelTest)
    fun inject(monthViewModelTest: MonthViewModelTest)
    fun inject(calendarDaoTest: CalendarDaoTest)
}