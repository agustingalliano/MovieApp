package com.example.movie_app.ui.viewmodel

import android.app.Application
import android.content.res.Resources
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.movie_app.R
import com.example.movie_app.data.MovieRepository
import com.example.movie_app.domain.model.Movie
import com.example.movie_app.domain.model.MovieDetail
import com.example.movie_app.domain.model.Session
import com.example.movie_app.listeners.MovieRepositoryCallBackListener
import java.math.BigDecimal
import java.math.BigInteger

class MovieViewModel (application: Application) : AndroidViewModel(application) {

    private val movieRepository: MovieRepository = MovieRepository(getApplication<Application>().applicationContext)
    val movies = MutableLiveData<List<Movie>>()
    val isLoading = MutableLiveData<Boolean>()
    val toastList = MutableLiveData<String>()
    val movieDetail = MutableLiveData<MovieDetail>()
    val toastRate = MutableLiveData<String>()
    val toastDetail = MutableLiveData<String>()
    var resources: Resources = getApplication<Application>().resources
    private var score: BigDecimal = BigDecimal.ZERO
    var currentPage = 1

    init {

        movieRepository.setMovieRepositoryListener(object : MovieRepositoryCallBackListener {
            override fun onSuccessDetail(detail: MovieDetail) {
                movieDetail.postValue(detail)
                isLoading.postValue(false)
            }

            override fun onFailureDetail(errorMessage: String) {
                toastDetail.postValue(resources.getString(R.string.movie_detail_failed))
                isLoading.postValue(false)
            }

            override fun onSuccessMovieList(movieList: List<Movie>) {
                movies.postValue(movieList)
                isLoading.postValue(false)
            }

            override fun onFailureMovieList() {
                isLoading.postValue(false)
                toastList.postValue(resources.getString(R.string.movie_list_failed))
            }

            override fun onResultRate(ok: Boolean) {
                isLoading.postValue(false)
                if(ok) toastRate.postValue(resources.getString(R.string.movie_rated_successfully)) else toastRate.postValue(resources.getString(R.string.movie_rated_failed))
            }

            override fun onResultSession(session: Session?, success: Boolean) {
                if(success) {
                    movieRepository.setScoreMovieById(movieDetail.value?.id ?: 0, score.toDouble(), session?.guestSession)
                } else {
                    isLoading.postValue(false)
                    toastRate.postValue(resources.getString(R.string.movie_rated_failed_token))
                }
            }
        })
    }

    fun onGetMovies() {
        isLoading.postValue(true)
        movieRepository.getMovies(currentPage)
    }

    fun getMovieById(id: Int) {
        isLoading.postValue(true)
        movieDetail.postValue(MovieDetail(0, "", "", "", "",
                                            "", 0.0, 0.0, ""))
        movieRepository.getMovieById(id)
    }

    fun setScore(score: String) {
        isLoading.postValue(true)
        this.score = if(score.isNotEmpty()) BigDecimal.valueOf(score.toDouble()) else BigDecimal.ZERO
        val intPart = this.score.toBigInteger()
        val decimalPart = this.score.subtract(BigDecimal(intPart))
        val isDecimalPart5 = decimalPart.compareTo(BigDecimal(0.5))==0
        val isDecimalPart0 = decimalPart.compareTo(BigDecimal(0.0))==0

        if(!isDecimalPart5 && !isDecimalPart0) {
            toastRate.postValue(resources.getString(R.string.value_expected_according_to_decimal_part))
            isLoading.postValue(false)
            return
        }

        if(this.score >= BigDecimal(0.5) && this.score <= BigDecimal(10) ) {
            movieRepository.getSession()
        } else {
            toastRate.postValue(resources.getString(R.string.value_expected_according_to_range))
            isLoading.postValue(false)
        }
    }
}