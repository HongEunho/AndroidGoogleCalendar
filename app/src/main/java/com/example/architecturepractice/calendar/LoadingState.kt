package com.example.architecturepractice.calendar

sealed class LoadingState {
    object UnInitialized: LoadingState()

    object Loading: LoadingState()

    object Success: LoadingState()
}