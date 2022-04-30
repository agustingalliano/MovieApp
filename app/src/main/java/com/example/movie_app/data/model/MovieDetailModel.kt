package com.example.movie_app.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetailModel(
    @SerializedName("id") val id: Int,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("release_date") val release_date: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("genres") val genres: List<GenreModel>?
)