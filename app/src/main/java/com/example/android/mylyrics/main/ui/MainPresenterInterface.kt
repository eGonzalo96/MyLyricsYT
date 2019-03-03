package com.example.android.mylyrics.main.ui

import android.content.Context

interface MainPresenterInterface {
    fun getLyricsFromServer(
        songName: String, artistName: String)

    fun getLyricsFromInternalStorage(
        songName: String, artistName: String, context: Context
    )
}