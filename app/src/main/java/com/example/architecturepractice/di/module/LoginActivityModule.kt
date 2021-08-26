package com.example.architecturepractice.di.module

import android.content.Context
import android.view.LayoutInflater
import com.example.architecturepractice.databinding.ActivityLoginBinding
import com.example.architecturepractice.di.ActivityContext
import com.example.architecturepractice.di.ActivityScope
import com.example.architecturepractice.di.ApplicationContext
import com.example.architecturepractice.signin.LoginActivity
import dagger.Module
import dagger.Provides

@Module
abstract class LoginActivityModule {
    companion object {

        @Provides
        @ActivityContext
        fun provideContext(activity: LoginActivity): Context {
            return activity
        }

    }
}