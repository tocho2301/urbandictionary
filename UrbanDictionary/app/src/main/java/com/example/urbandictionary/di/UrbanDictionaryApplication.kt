package com.example.urbandictionary.di

import android.app.Application
import com.example.urbandictionary.di.modules.ActivityModule
import com.example.urbandictionary.di.modules.AppModule
import com.example.urbandictionary.di.modules.RepositoryModule

class UrbanDictionaryApplication : Application() {
    lateinit var applicationComponent : ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
            .activityModule(ActivityModule())
            .appModule(AppModule(this))
            .repositoryModule(RepositoryModule())
            .build()
    }

    fun getComponent() = applicationComponent
}