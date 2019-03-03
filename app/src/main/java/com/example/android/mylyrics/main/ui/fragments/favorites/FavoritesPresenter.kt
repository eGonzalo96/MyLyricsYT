package com.example.android.mylyrics.main.ui.fragments.favorites

import android.content.Context
import com.example.android.mylyrics.main.ui.fragments.favorites.entities.LyricsInformation

class FavoritesPresenter
    (val mFavoritesView: FavoritesView): Presenter {

    val mFavoritesModel = FavoritesModel()

    override fun getSavedLyricsList(context: Context): ArrayList<LyricsInformation> {

        val savedLyrics = ArrayList<LyricsInformation>(0)

        for(i in context.filesDir.list()) {
            savedLyrics.add(
                LyricsInformation(
                    i.toString().split("+")[0],
                    i.toString().split("+")[1]
                )
            )
        }

        return mFavoritesModel
            .formatSavedLyricsInformation(savedLyrics)
    }

}