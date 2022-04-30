package com.example.movie_app.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.movie_app.data.database.entities.SessionEntity

@Dao
interface SessionDao {

    @Query("SELECT * FROM session_table LIMIT 1")
    fun getSession(): SessionEntity?

    @Insert
    fun insertSession(sessionEntity: SessionEntity)
}