package com.example.android.mylyrics.main.ui

import android.content.Context
import com.example.android.mylyrics.main.data.entities.Song
import com.example.android.mylyrics.main.ui.MainPresenterInterface

class MainPresenter(val mMainView: MainView) : MainPresenterInterface {

    val mMainModel = MainModel()

    override fun getLyricsFromServer(
        songName: String,
        artistName: String) {

        val formattedSongName = formatInformationString(songName)
        val formattedArtistName = formatInformationString(artistName)

        mMainModel.getSongEntityFromAPI(
            formattedSongName,
            formattedArtistName,
            object: MainModel.MainModelListener {

                override fun onSuccess(song: Song) {
                    mMainView.displayLyrics(song,
                        formattedSongName, formattedArtistName)
                }

                override fun onFailure() {
                    mMainView.displayErrorMessage()
                }
            })
    }


    override fun getLyricsFromInternalStorage(
            songName: String, artistName: String, context: Context) {

        val formattedSongName = formatInformationString(songName)
        val formattedArtistName = formatInformationString(artistName)

        mMainModel
            .getSongLyricsFromInternalStorage(
                formattedSongName, formattedArtistName,
                    object: MainModel.MainModelListener {

                        override fun onSuccess(song: Song) {
                            mMainView.displayLyrics(song,
                                formattedSongName, formattedArtistName)
                        }

                        override fun onFailure() {
                            mMainView.displayErrorMessage()
                        }
                    }, context
            )

    }




    private fun formatInformationString(info: String) : String {
        return  info.toLowerCase().trim().split(" ").joinToString (separator = "_") {
                it -> "${it}"
        }
    }
}