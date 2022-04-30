package com.example.movie_app.util

import com.example.movie_app.data.database.entities.MovieDetailEntity
import com.example.movie_app.data.database.entities.SessionEntity
import com.example.movie_app.data.model.MovieDetailModel
import com.example.movie_app.data.model.MovieModel
import com.example.movie_app.data.model.SessionModel
import com.example.movie_app.domain.model.Movie
import com.example.movie_app.domain.model.MovieDetail
import com.example.movie_app.domain.model.Session

fun MovieModel.toDomain() = Movie(title?: "Title not found", id, posterPath?: "")

fun MovieDetail.toEntity() = MovieDetailEntity(id, originalTitle, overview, release_date, backdropPath, originalLanguage, popularity, voteAverage, genres)

fun MovieDetailModel.toDomain(): MovieDetail {
    val list = genres?.map {it.name} ?: emptyList()
    val separator = (", ")
    val string = list.joinToString(separator)
    return MovieDetail(id,
        originalTitle?: "Value Not Found",
        overview?: "Value Not Found",
        release_date?: "Value Not Found",
        backdropPath?: "",
        originalLanguage?: "",
        popularity?: 0.0,
        voteAverage?: 0.0,
        string.ifEmpty { "Value Not Found" })
}

fun MovieDetailEntity.toDomain() = MovieDetail(id, originalTitle, overview, release_date, backdropPath, originalLanguage, popularity, voteAverage, genres)

fun Session.toEntity() = SessionEntity(guestSession)

fun SessionModel.toDomain() = Session(guestSessionId)

fun SessionEntity.toDomain() = Session(guestSessionId)
