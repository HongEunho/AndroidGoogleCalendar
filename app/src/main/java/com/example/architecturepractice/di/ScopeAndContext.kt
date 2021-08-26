package com.example.architecturepractice.di

import java.lang.annotation.RetentionPolicy
import javax.inject.Qualifier
import javax.inject.Scope

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ActivityScope

@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class FragmentScope

@Qualifier
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ActivityContext

@Qualifier
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ApplicationContext

@Qualifier
@Retention(value = AnnotationRetention.RUNTIME)
annotation class DatabaseInfo