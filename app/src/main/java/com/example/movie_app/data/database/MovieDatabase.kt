package com.example.movie_app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.movie_app.data.database.dao.MovieDetailDao
import com.example.movie_app.data.database.dao.SessionDao
import com.example.movie_app.data.database.entities.MovieDetailEntity
import com.example.movie_app.data.database.entities.SessionEntity

@Database(entities = [MovieDetailEntity::class, SessionEntity::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {

    abstract fun getMovieDetailDao(): MovieDetailDao
    abstract fun getSessionDao(): SessionDao
}