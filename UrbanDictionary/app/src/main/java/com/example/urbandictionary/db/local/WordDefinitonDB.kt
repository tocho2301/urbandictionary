package com.example.urbandictionary.db.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.urbandictionary.entity.WordDefinition

@Database(
    entities = [WordDefinition::class],
    version = 1
)
abstract class WordDefinitonDB : RoomDatabase(){
    abstract fun wordDefinitionDAO() :  WordDefinitionDAO
}