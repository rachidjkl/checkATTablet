package com.example.checkattablet.ApiAcces

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    fun getInstance(): Retrofit
    {
        return  Retrofit.Builder().baseUrl(ApiGets.BASE_URL).
        addConverterFactory(GsonConverterFactory.create()).
        build()
    }
}