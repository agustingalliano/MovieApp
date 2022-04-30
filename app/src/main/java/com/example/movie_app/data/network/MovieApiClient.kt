package com.example.movie_app.data.network

import com.example.movie_app.data.model.MovieDetailModel
import com.example.movie_app.data.network.response.MovieResponse
import com.example.movie_app.data.model.SessionModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface MovieApiClient {

    @GET("movie/popular")
    fun getMovies(@Query("page") page: Int=1, @Query ("api_key") key: String): Call<MovieResponse>

    @GET("movie/{id}")
    fun getMovieById(@Path("id") id: Int, @Query ("api_key") key: String): Call<MovieDetailModel>

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("movie/{movie_id}/rating")
    fun rateMovieById(@Path("movie_id") movie_id: Int, @Query("api_key") key: String, @Query("guest_session_id") guest_session_id: String?, @Body body: HashMap<String, Double>): Call<ResponseBody>

    @GET("authentication/guest_session/new")
    fun getGuestSession(@Query("api_key") key: String): Call<SessionModel>
}