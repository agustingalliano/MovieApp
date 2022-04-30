package com.example.movie_app.data

import android.content.Context
import com.example.movie_app.data.database.entities.MovieDetailEntity
import com.example.movie_app.data.database.entities.SessionEntity
import com.example.movie_app.data.model.MovieDetailModel
import com.example.movie_app.data.model.SessionModel
import com.example.movie_app.data.network.response.MovieResponse
import com.example.movie_app.data.network.MovieService
import com.example.movie_app.data.network.SessionService
import com.example.movie_app.di.RoomModule
import com.example.movie_app.domain.model.MovieDetail
import com.example.movie_app.listeners.MovieRepositoryCallBackListener
import com.example.movie_app.util.toDomain
import com.example.movie_app.util.toEntity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository(context: Context) {

    private val movieService = MovieService()
    private val sessionService = SessionService()
    private val movieDetailDao = RoomModule.provideMovieDetailDao(context)
    private val sessionDao = RoomModule.provideSessionDao(context)
    private lateinit var movieRepositoryCallBackListener: MovieRepositoryCallBackListener

    fun getMovies(page: Int) {
        val response = movieService.getMovies(page)
        response.enqueue(object :Callback<MovieResponse>{
            override fun onResponse(
                call: Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                response.body()?.let { resultResponse ->
                    movieRepositoryCallBackListener.onSuccessMovieList(resultResponse.results.toList().map { it.toDomain() })
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                movieRepositoryCallBackListener.onFailureMovieList()
            }

        })
    }

    fun getMovieById(id: Int) {

        val movieDetailEntity: MovieDetailEntity? = movieDetailDao.getMovieDetail(id)

        if(movieDetailEntity!=null) {
            movieRepositoryCallBackListener.onSuccessDetail(movieDetailEntity.toDomain())
            return
        }

        val response = movieService.getMovieById(id)
        var movieDetail: MovieDetail?
        response.enqueue(object : Callback<MovieDetailModel> {
            override fun onResponse(
                call: Call<MovieDetailModel>,
                response: Response<MovieDetailModel>
            ) {
                if(response.body() == null) {
                    movieRepositoryCallBackListener.onFailureDetail("Error")
                }
                response.body()?.let { resultResponse ->
                    movieDetail = resultResponse.toDomain()
                    movieDetailDao.insertMovieDetail(movieDetail!!.toEntity())
                    movieRepositoryCallBackListener.onSuccessDetail(movieDetail!!)
                }
            }

            override fun onFailure(call: Call<MovieDetailModel>, t: Throwable) {
                movieRepositoryCallBackListener.onFailureDetail("Error")
            }

        })
    }

    fun setScoreMovieById(id: Int, score: Double, guestSession: String?) {

        val response = movieService.rateMovieById(id, score, guestSession)

        response.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                movieRepositoryCallBackListener.onResultRate(response.isSuccessful)
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                movieRepositoryCallBackListener.onResultRate(false)
            }

        })
    }

    fun getSession() {

        val sessionEntity: SessionEntity? = sessionDao.getSession()

        if(sessionEntity!=null) {
            movieRepositoryCallBackListener.onResultSession(sessionEntity.toDomain(), true)
            return
        }

        val session = sessionService.getGuestSession()

        session.enqueue(object : Callback<SessionModel> {
            override fun onResponse(call: Call<SessionModel>, response: Response<SessionModel>) {
                val success = response.body()?.success ?: false
                if (success) response.body()?.toDomain()?.toEntity()?.let { sessionDao.insertSession(it) }
                response.body()?.toDomain()?.let { movieRepositoryCallBackListener.onResultSession(it, success) }
            }

            override fun onFailure(call: Call<SessionModel>, t: Throwable) {
                movieRepositoryCallBackListener.onResultSession(null, false)
            }

        })
    }

    fun setMovieRepositoryListener(movieRepositoryCallBackListener: MovieRepositoryCallBackListener) {
        this.movieRepositoryCallBackListener = movieRepositoryCallBackListener
    }
}