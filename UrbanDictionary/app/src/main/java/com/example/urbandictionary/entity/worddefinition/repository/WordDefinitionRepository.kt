package com.example.urbandictionary.entity.worddefinition.repository

import com.example.urbandictionary.BuildConfig
import com.example.urbandictionary.db.local.WordDefinitonDB
import com.example.urbandictionary.entity.worddefinition.WordDefinition
import com.example.urbandictionary.entity.worddefinition.retrofit.WordDefinitionRetrofitModule
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WordDefinitionRepository(
    private var wordDefinitionRetrofitModule: WordDefinitionRetrofitModule,
    private var wordDefinitonDB: WordDefinitonDB
    ) :
    IWordDefinitionRepository {


    override fun getDefinitionsFromServer(word: String?): Observable<ArrayList<WordDefinition>> {
        return wordDefinitionRetrofitModule.getWordDefinitions()
            .getDefinitions(BuildConfig.URBANDICTIONARY_API_KEY, BuildConfig.URBANDICTIONARY_API_HOST, word)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { getWordDefinitionResponse ->
                if (getWordDefinitionResponse.list.size>0){
                    return@flatMap Observable.just(getWordDefinitionResponse.list)
                }else{
                    throw Exception("We couldn´t find any definition for $word")
                }
            }
    }

    override fun saveDefinitonInCache(wordDefinitionList : ArrayList<WordDefinition>): Observable<Int> {
        return wordDefinitonDB.wordDefinitionDAO().saveWordDefinitionList(wordDefinitionList)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()

    }

    override fun getDefinitionsFromCache(word: String): Observable<ArrayList<WordDefinition>> {
        return wordDefinitonDB.wordDefinitionDAO().searchWordDefinition(word)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap {
                return@flatMap Observable.just(ArrayList(it))
            }
    }

}