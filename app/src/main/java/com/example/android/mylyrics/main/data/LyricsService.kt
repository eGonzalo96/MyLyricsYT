package com.example.android.mylyrics.main.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import com.example.android.mylyrics.main.data.entities.Song

interface LyricsService {

    @GET("{artist}/{song}")
    fun getLyrics(@Path("artist") artistName: String,
                  @Path("song") songName: String): Call<Song>

}