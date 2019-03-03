package com.example.android.mylyrics.lyrics

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.android.mylyrics.R
import com.example.android.mylyrics.storagemanager.LyricsStorageManager
import kotlinx.android.synthetic.main.activity_lyrics.*
import java.io.File

class LyricsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lyrics)

        val lyrics = intent.getStringExtra("lyrics")
        val songName = intent.getStringExtra("song")
        val artistName = intent.getStringExtra("artist")

        if(File(filesDir, songName + "+" + artistName).exists()) {
            addLyrics.setImageResource(R.drawable.ic_favorite_white_24dp)
        }

        lyricsActivity_textView_lyrics.text = lyrics
        supportActionBar?.title = formatSongName(songName, artistName)
        addLyrics.setOnClickListener { v->
            handleLyricsOnStorage(songName, artistName, lyrics)
        }
    }


    private fun handleLyricsOnStorage(
        songName: String, artistName: String, lyrics: String) {

        val lyricsFile = File(filesDir, songName + "+" + artistName)

        if(!lyricsFile.exists()) {
            LyricsStorageManager.saveLyricsOnStorage(
                lyricsFile, lyrics, baseContext)
            addLyrics.setImageResource(R.drawable.ic_favorite_white_24dp)
        } else {
            LyricsStorageManager.deleteLyricsFromStorage(
                lyricsFile)
            addLyrics.setImageResource(R.drawable.ic_add_white_24dp)
        }
    }


    private fun formatSongName(
        songName: String, artistName: String) : String {

        val song = ArrayList<String>(0)
        val artist = ArrayList<String>(0)

        for(i in songName.split("_")) {
            song.add(i.substring(0, 1).toUpperCase() + i.substring(1))
        }
        val formattedSong = song.joinToString (separator = " ") { it -> "${it} " }

        for(i in artistName.split("_")) {
            artist.add(i.substring(0, 1).toUpperCase() + i.substring(1))
        }
        val formattedArtist = artist.joinToString (separator = " ") { it -> "${it} " }

        return formattedSong + " - " + formattedArtist
    }
}
