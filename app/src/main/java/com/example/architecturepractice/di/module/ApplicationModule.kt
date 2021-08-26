package com.example.architecturepractice.di.module

import android.content.Context
import com.example.architecturepractice.MyApplication
import dagger.Binds
import dagger.Module

@Module(includes = [ViewModelModule::class, RepositoryModule::class, DatabaseModule::class])
abstract class ApplicationModule {

    @Binds
    abstract fun bindsContext(application: MyApplication): Context

}