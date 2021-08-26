package com.example.architecturepractice.di.module

import android.content.Context
import com.example.architecturepractice.PreferenceManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
public class PreferenceModule {

    @Provides
    @Singleton
    fun providePreferenceManager(context: Context): PreferenceManager {
        return PreferenceManager(context)
    }

}