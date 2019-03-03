package com.example.android.mylyrics.storagemanager

import android.content.Context
import android.util.Log
import java.io.File

class LyricsStorageManager {

    companion object {

        fun saveLyricsOnStorage(song: File, lyrics: String, context: Context) {

            if(!song.exists()) {
                context.openFileOutput(song.name, Context.MODE_PRIVATE).use {
                    it.write(lyrics.toByteArray())
                }
            }
        }


        fun deleteLyricsFromStorage(song: File) {
            if(song.exists()) {
                song.delete()
            }
        }

    }

}