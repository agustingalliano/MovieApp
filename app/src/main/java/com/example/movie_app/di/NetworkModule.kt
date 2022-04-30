package com.example.movie_app.di

import com.example.movie_app.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().
        baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}