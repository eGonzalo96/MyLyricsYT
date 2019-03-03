package com.example.android.mylyrics.main.ui

import android.content.Context
import android.util.Log
import com.example.android.mylyrics.BuildConfig
import com.example.android.mylyrics.main.data.LyricsService
import com.example.android.mylyrics.main.data.entities.Song
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import javax.xml.datatype.DatatypeConstants.SECONDS
import javax.xml.datatype.DatatypeConstants.MINUTES
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit




class MainModel {

    lateinit var mLyricsService: LyricsService

    //init {


    //}

    interface MainModelListener {
        fun onSuccess(song: Song)
        fun onFailure()
    }


    fun getSongEntityFromAPI(songName: String,
                      artistName: String,
                      listener: MainModelListener) {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build()

        val retrofitObject = Retrofit
            .Builder()
            .client(okHttpClient)
            .baseUrl("https://api.lyrics.ovh/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mLyricsService = retrofitObject.create(LyricsService::class.java)

        mLyricsService
            .getLyrics(artistName, songName)
            .enqueue(object: Callback<Song> {

                override fun onFailure(call: Call<Song>, t: Throwable) {
                    listener.onFailure()
                }

                override fun onResponse(call: Call<Song>, response: Response<Song>) {
                    if(response.isSuccessful) {
                        val song = response.body()
                        if(response.code() == 200 && song != null) {
                            listener.onSuccess(song)
                        } else if(response.code() == 404){
                            listener.onFailure()
                        }
                    }
                }

            })
    }


    fun getSongLyricsFromInternalStorage(
        songName: String, artistName: String, listener: MainModelListener, context: Context) {

        var fileInputStream: FileInputStream? = null
        fileInputStream = context.openFileInput(songName + "+" + artistName)
        var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var lyric: String? = null
        while ({ lyric = bufferedReader.readLine(); lyric }() != null) {
            stringBuilder.append(lyric+ "\n")
        }

        listener.onSuccess(Song(stringBuilder.toString()))
    }

}