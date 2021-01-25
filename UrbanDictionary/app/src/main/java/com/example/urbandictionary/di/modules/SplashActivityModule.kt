package com.example.urbandictionary.di.modules

import com.example.urbandictionary.ui.activity.splash.SplashViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import java.util.*

@Module
@InstallIn(ActivityRetainedComponent::class)
class SplashActivityModule {

    @Provides
    fun viewModelProvider(timer: Timer) = SplashViewModel(timer)

    @Provides
    fun timerProvider() = Timer()

}