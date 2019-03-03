package com.example.android.mylyrics.main.ui.fragments.favorites

import com.example.android.mylyrics.main.ui.fragments.favorites.entities.LyricsInformation

class FavoritesModel {

    fun formatSavedLyricsInformation(list: ArrayList<LyricsInformation>):
            ArrayList<LyricsInformation> {

        // chapel_of_love
        // the_dixie_cups

        var songName: String
        var artistName: String
        val newList = ArrayList<LyricsInformation>(0)

        for(i in list) {
            songName = i.songName.split("_").joinToString(separator = " ")
                { it -> "${it.substring(0, 1).toUpperCase()}" + "${it.substring(1)}" }

            artistName = i.artistName.split("_").joinToString(separator = " ")
                { it -> "${it.substring(0, 1).toUpperCase()}" + "${it.substring(1)}" }

            newList.add(LyricsInformation(songName, artistName))
        }

        return newList
    }

}