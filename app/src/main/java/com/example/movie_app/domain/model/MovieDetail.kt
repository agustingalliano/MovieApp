package com.example.movie_app.domain.model

data class MovieDetail(
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val release_date: String,
    val backdropPath: String,
    val originalLanguage: String,
    val popularity: Double,
    val voteAverage: Double,
    val genres: String
)