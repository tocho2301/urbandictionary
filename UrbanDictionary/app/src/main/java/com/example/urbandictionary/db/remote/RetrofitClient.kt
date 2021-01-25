package com.example.urbandictionary.db.remote
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {

    fun getClient(URL: String?, client: OkHttpClient): Retrofit? {
        return Retrofit.Builder()
            .baseUrl(URL!!)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun getHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor)
            .readTimeout(60, TimeUnit.SECONDS).connectTimeout(60, TimeUnit.SECONDS).build()
    }

    fun getDefaultUrl() : String{
        return "https://mashape-community-urban-dictionary.p.rapidapi.com/"
    }


}