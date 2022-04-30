package com.example.movie_app.listeners

import com.example.movie_app.domain.model.Movie
import com.example.movie_app.domain.model.MovieDetail
import com.example.movie_app.domain.model.Session

interface MovieRepositoryCallBackListener {

    fun onSuccessDetail(detail: MovieDetail)
    fun onFailureDetail(errorMessage: String)
    fun onSuccessMovieList(movieList: List<Movie>)
    fun onFailureMovieList()
    fun onResultRate(ok: Boolean)
    fun onResultSession(session: Session?, success: Boolean)
}