package com.example.movie_app.data.model

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("title") val title:String?,
    @SerializedName("id") val id: Int,
    @SerializedName("poster_path") val posterPath: String?
)