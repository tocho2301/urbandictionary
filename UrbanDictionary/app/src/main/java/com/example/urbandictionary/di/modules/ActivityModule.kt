package com.example.urbandictionary.di.modules

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.urbandictionary.entity.WordDefinition
import com.example.urbandictionary.ui.adapter.WordDefinitionAdapter
import dagger.Module
import dagger.Provides
import java.util.*
import kotlin.collections.ArrayList

@Module
class ActivityModule {

    @Provides
    fun wordDefinitionAdapterProvider(list: ArrayList<WordDefinition>) = WordDefinitionAdapter(list)

    @Provides
    fun emptyListProvider() = ArrayList<WordDefinition>()

    @Provides
    fun linearLayoutProvider(context: Context) = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

    @Provides
    fun timerProvider() = Timer()
}