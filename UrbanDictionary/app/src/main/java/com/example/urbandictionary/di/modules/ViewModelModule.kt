package com.example.urbandictionary.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.urbandictionary.di.ViewModelFactory
import com.example.urbandictionary.di.ViewModelKey
import com.example.urbandictionary.entity.WordDefinition
import com.example.urbandictionary.ui.activity.home.HomeViewModel
import com.example.urbandictionary.ui.activity.splash.SplashViewModel
import com.example.urbandictionary.ui.adapter.WordDefinitionAdapter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(splashViewModel: SplashViewModel) : ViewModel

    @Binds
    abstract fun bindViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory
}