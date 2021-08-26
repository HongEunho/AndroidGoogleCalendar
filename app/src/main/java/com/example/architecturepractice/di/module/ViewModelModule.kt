package com.example.architecturepractice.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.architecturepractice.calendar.CalendarViewModel
import com.example.architecturepractice.calendar.MonthViewModel
import com.example.architecturepractice.customview.model.ShareViewModel
import com.example.architecturepractice.di.AppViewModelFactory
import com.example.architecturepractice.di.ViewModelKey
import com.example.architecturepractice.signin.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
public abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CalendarViewModel::class)
    abstract fun bindCalendarViewModel(viewModel: CalendarViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MonthViewModel::class)
    abstract fun bindMonthViewModel(viewModel: MonthViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShareViewModel::class)
    abstract fun bindShareViewModel(viewModel: ShareViewModel): ViewModel
}