package com.example.architecturepractice.di

import android.content.Context
import com.example.architecturepractice.MyApplication
import com.example.architecturepractice.TestApplication
import com.example.architecturepractice.di.module.DatabaseModule
import com.example.architecturepractice.di.module.RepositoryModule
import com.example.architecturepractice.di.module.ViewModelModule
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [ViewModelModule::class, RepositoryModule::class, DatabaseModule::class])
abstract class TestApplicationModule {


}