package com.example.android.mylyrics.main.ui

import com.example.android.mylyrics.main.data.entities.Song

interface MainView {
    fun displayLyrics(lyrics: Song,
                      songName: String, artistName: String)
    fun displayErrorMessage()
}