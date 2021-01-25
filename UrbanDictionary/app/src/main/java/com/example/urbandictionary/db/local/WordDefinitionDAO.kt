package com.example.urbandictionary.db.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.urbandictionary.entity.WordDefinition
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

@Dao
interface WordDefinitionDAO {

    @Query("SELECT * FROM WordDefinition where word = :word")
    fun searchWordDefinition(word : String) : Observable<List<WordDefinition>>

    @Insert
    fun saveWordDefinitionList(list: List<WordDefinition>) : Completable

}