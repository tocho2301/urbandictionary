package com.example.urbandictionary.entity.worddefinition.repository

import com.example.urbandictionary.entity.WordDefinition
import io.reactivex.Observable

interface IWordDefinitionRepository {
    fun getDefinitionsFromServer(word : String) : Observable<ArrayList<WordDefinition>>
    fun saveDefinitonInCache(wordDefinitionList: ArrayList<WordDefinition>) : Observable<Int>
    fun getDefinitionsFromCache(word: String) : Observable<ArrayList<WordDefinition>>
}