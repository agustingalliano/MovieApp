package com.example.movie_app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movie_app.data.database.entities.MovieDetailEntity

@Dao
interface MovieDetailDao {

    @Query("SELECT * FROM movie_detail_table where id=:id")
    fun getMovieDetail(id: Int): MovieDetailEntity?

    @Insert
    fun insertMovieDetail(movieDetailEntity: MovieDetailEntity)
}