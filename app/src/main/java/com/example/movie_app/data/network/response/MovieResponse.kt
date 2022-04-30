package com.example.movie_app.data.network.response

import com.example.movie_app.data.model.MovieModel
import com.google.gson.annotations.SerializedName

data class MovieResponse(@SerializedName("results") val results: MutableList<MovieModel>)