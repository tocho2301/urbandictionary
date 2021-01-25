package com.example.urbandictionary.entity.worddefinition.retrofit

import com.example.urbandictionary.entity.worddefinition.retrofit.response.GetWordDefinitionsResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WordDefinitionApi {


    interface GetWordDefinitions{
        @GET("define")
        fun getDefinitions(@Header("x-rapidapi-key") apiKey:String,
                           @Header("x-rapidapi-host") apiHost:String,
                           @Query("term") word : String) : Observable<GetWordDefinitionsResponse>
    }
}