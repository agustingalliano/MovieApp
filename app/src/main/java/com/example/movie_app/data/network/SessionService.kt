package com.example.movie_app.data.network

import com.example.movie_app.data.model.SessionModel
import com.example.movie_app.di.NetworkModule
import com.example.movie_app.util.Constants
import retrofit2.Call

class SessionService {

    private val retrofit = NetworkModule.provideRetrofit()

    fun getGuestSession(): Call<SessionModel> {
        return retrofit.create(MovieApiClient::class.java).getGuestSession(Constants.API_KEY)
    }
}