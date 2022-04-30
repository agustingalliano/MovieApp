package com.example.movie_app.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_detail_table")
data class MovieDetailEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "original_title") val originalTitle: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "release_date") val release_date: String,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String,
    @ColumnInfo(name = "original_language") val originalLanguage: String,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "genres") val genres: String
)