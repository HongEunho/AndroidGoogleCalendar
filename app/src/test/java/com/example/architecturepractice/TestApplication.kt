package com.example.architecturepractice

import com.example.architecturepractice.di.DaggerTestApplicationComponent
import com.example.architecturepractice.di.component.DaggerApplicationComponent
import com.example.architecturepractice.viewmodel.CalendarViewModelTest
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class TestApplication: MyApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerTestApplicationComponent.factory().create(this)
    }

}