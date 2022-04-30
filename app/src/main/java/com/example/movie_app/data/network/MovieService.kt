package com.example.movie_app.data.network

import com.example.movie_app.data.model.MovieDetailModel
import com.example.movie_app.data.network.response.MovieResponse
import com.example.movie_app.di.NetworkModule
import com.example.movie_app.util.Constants.Companion.API_KEY
import okhttp3.ResponseBody
import retrofit2.Call

class MovieService {

    private val retrofit = NetworkModule.provideRetrofit()

    fun getMovies(page: Int) : Call<MovieResponse> {
        return retrofit.create(MovieApiClient::class.java).getMovies(page, API_KEY)
    }

    fun getMovieById(id: Int): Call<MovieDetailModel> {
        return retrofit.create(MovieApiClient::class.java).getMovieById(id, API_KEY)
    }

    fun rateMovieById(id: Int, scoreNumber: Double, guestSession: String?): Call<ResponseBody> {
        val body = HashMap<String, Double>()
        body["value"] = scoreNumber
        return retrofit.create(MovieApiClient::class.java).rateMovieById(id,
            API_KEY, guestSession, body)
    }
}