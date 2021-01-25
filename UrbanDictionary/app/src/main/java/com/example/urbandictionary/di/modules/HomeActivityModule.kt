package com.example.urbandictionary.di.modules
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.urbandictionary.db.local.WordDefinitonDB
import com.example.urbandictionary.db.remote.RetrofitClient
import com.example.urbandictionary.entity.WordDefinition
import com.example.urbandictionary.entity.worddefinition.repository.WordDefinitionRepository
import com.example.urbandictionary.entity.worddefinition.retrofit.WordDefinitionRetrofitModule
import com.example.urbandictionary.ui.activity.home.HomeViewModel
import com.example.urbandictionary.ui.adapter.WordDefinitionAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(ActivityRetainedComponent::class)
class HomeActivityModule {

    @Provides
    fun mainViewModelProvider(wordDefinitionRepository: WordDefinitionRepository) = HomeViewModel(wordDefinitionRepository)

    @Provides
    fun wordDefinitionRepositoryProvider(wordDefinitionRetrofitModule: WordDefinitionRetrofitModule, wordDefinitonDB: WordDefinitonDB) = WordDefinitionRepository(wordDefinitionRetrofitModule, wordDefinitonDB)

    @Provides
    fun retrofitClientProvider() = RetrofitClient()

    @Provides
    fun wordDefinitionRetrofitModuleProvider(retrofitClient: RetrofitClient) = WordDefinitionRetrofitModule(retrofitClient)

    @Provides
    fun wordDefinitionAdapterProvider(arrayList: ArrayList<WordDefinition>) = WordDefinitionAdapter(arrayList)

    @Provides
    fun worDefinitionsArrayProvider() = ArrayList<WordDefinition>()

    @Provides
    fun layoutManagerProvider(@ApplicationContext context: Context) = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)

    @Provides
    fun wordDefinitionLocalDb(@ApplicationContext applicationContext:Context) = Room.databaseBuilder(
        applicationContext,
        WordDefinitonDB::class.java, "database-name"
    ).build()
}