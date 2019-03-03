package com.example.android.mylyrics.main.data.entities

import com.google.gson.annotations.SerializedName

data class Song(
    @SerializedName("lyrics")
    val lyrics: String
)