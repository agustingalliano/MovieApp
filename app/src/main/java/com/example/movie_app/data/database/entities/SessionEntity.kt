package com.example.movie_app.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "session_table")
data class SessionEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "guest_session_id") val guestSessionId: String = ""
)