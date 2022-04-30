package com.example.movie_app.data.model

import com.google.gson.annotations.SerializedName

data class SessionModel(
    @SerializedName("success") val success:Boolean,
    @SerializedName("guest_session_id") val guestSessionId: String
)