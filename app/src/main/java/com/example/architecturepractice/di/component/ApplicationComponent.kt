package com.example.architecturepractice.di.component

import com.example.architecturepractice.MyApplication
import com.example.architecturepractice.di.module.ActivityModule
import com.example.architecturepractice.di.module.ApplicationModule
import com.example.architecturepractice.di.module.PreferenceModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ApplicationModule::class, ActivityModule::class, PreferenceModule::class])
public interface ApplicationComponent: AndroidInjector<MyApplication> {

    @Component.Factory
    interface Factory: AndroidInjector.Factory<MyApplication>
}