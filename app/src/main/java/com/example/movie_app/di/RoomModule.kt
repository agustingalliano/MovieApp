package com.example.movie_app.di

import android.content.Context
import androidx.room.Room
import com.example.movie_app.data.database.MovieDatabase

object RoomModule {

    private const val MOVIE_DATABASE_NAME = "movie_database"

    private fun provideRoom(context: Context) = Room.databaseBuilder(context, MovieDatabase::class.java, MOVIE_DATABASE_NAME).allowMainThreadQueries().build()

    fun provideMovieDetailDao(context: Context) = provideRoom(context).getMovieDetailDao()

    fun provideSessionDao(context: Context) = provideRoom(context).getSessionDao()
}