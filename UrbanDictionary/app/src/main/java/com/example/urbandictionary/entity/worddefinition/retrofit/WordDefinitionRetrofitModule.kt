package com.example.urbandictionary.entity.worddefinition.retrofit

import com.example.urbandictionary.db.remote.RetrofitClient

class WordDefinitionRetrofitModule (var retrofitClient: RetrofitClient){

    fun getWordDefinitions() : WordDefinitionApi.GetWordDefinitions{
        return retrofitClient.getClient(retrofitClient.getDefaultUrl(), retrofitClient.getHttpClient())!!
            .create(WordDefinitionApi.GetWordDefinitions::class.java)
    }
}