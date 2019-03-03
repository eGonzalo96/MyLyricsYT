package com.example.android.mylyrics.main.ui.fragments.favorites

import android.content.Context
import com.example.android.mylyrics.main.ui.fragments.favorites.entities.LyricsInformation

interface Presenter {
    fun getSavedLyricsList(context: Context): ArrayList<LyricsInformation>
}