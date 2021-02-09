package com.example.urbandictionary.di.modules

import android.content.Context
import androidx.room.Room
import com.example.urbandictionary.db.local.WordDefinitonDB
import com.example.urbandictionary.db.remote.RetrofitClient
import com.example.urbandictionary.entity.worddefinition.repository.WordDefinitionRepository
import com.example.urbandictionary.entity.worddefinition.retrofit.WordDefinitionRetrofitModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    fun wordDefinitionRepositoryProvider(
        wordDefinitionRetrofitModule: WordDefinitionRetrofitModule,
        worDefinitionDB: WordDefinitonDB
    ) = WordDefinitionRepository(wordDefinitionRetrofitModule,worDefinitionDB)

    @Provides
    fun wordDefinitionRetrofitModuleProvider(
        retrofitClient: RetrofitClient
    ) = WordDefinitionRetrofitModule(retrofitClient)

    @Provides
    fun retrofitClientProvider() = RetrofitClient()

    @Provides
    fun wordDefinitionDbProvider(context: Context) = Room.databaseBuilder(
    context,
    WordDefinitonDB::class.java, "word-definiton-db"
    ).build()
}