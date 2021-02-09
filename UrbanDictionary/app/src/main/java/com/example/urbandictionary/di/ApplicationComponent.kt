package com.example.urbandictionary.di

import com.example.urbandictionary.di.modules.ActivityModule
import com.example.urbandictionary.di.modules.ViewModelModule
import com.example.urbandictionary.di.modules.AppModule
import com.example.urbandictionary.di.modules.RepositoryModule
import com.example.urbandictionary.ui.activity.home.HomeActivity
import com.example.urbandictionary.ui.activity.splash.SplashActivity
import dagger.Component

@Component(modules = [
    ViewModelModule::class,
    AppModule::class,
    RepositoryModule::class,
    ActivityModule::class
])
interface ApplicationComponent {
    fun inject (target: HomeActivity)
    fun inject (target: SplashActivity)
}