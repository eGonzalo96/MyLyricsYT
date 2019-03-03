package com.example.android.mylyrics.main.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.android.mylyrics.R
import com.example.android.mylyrics.lyrics.LyricsActivity
import com.example.android.mylyrics.main.data.entities.Song
import com.example.android.mylyrics.main.ui.fragments.favorites.FavoritesFragment
import com.example.android.mylyrics.main.ui.fragments.HomeFragment
import com.example.android.mylyrics.storagemanager.LyricsStorageManager
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity :
    AppCompatActivity(),
    HomeFragment.OnHomeFragmentInteractionListener,
    FavoritesFragment.OnFavsFragmentInteractionListener,
    MainView {

    private val mMainPresenter = MainPresenter(this)
    private val mFragmentManager = supportFragmentManager
    private val mFavoritesFragment = FavoritesFragment()
    private val mHomeFragment = HomeFragment()
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {

            R.id.navigation_home -> {
                changeFragment(R.id.navigation_home)
                return@OnNavigationItemSelectedListener  true
            }

            R.id.navigation_favorites -> {
                changeFragment(R.id.navigation_favorites)
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFragmentManager
            .beginTransaction()
            .add(R.id.mainAcitity_fragment, mHomeFragment)
            .commitNow()

        mHomeFragment.mHomeListener = this
        mFavoritesFragment.mFragmentInteractionListener = this

        mainActivity_navigation
            .setOnNavigationItemSelectedListener(
                mOnNavigationItemSelectedListener)

    }


    // Function used to manage fragments in Navigation Bar
    private fun changeFragment(itemId: Int) {

        val newFragment = when(itemId) {
            R.id.navigation_favorites-> mFavoritesFragment
            R.id.navigation_home-> mHomeFragment
            else -> mHomeFragment
        }

        mFragmentManager
            .beginTransaction()
            .replace(R.id.mainAcitity_fragment, newFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            .commitNow()
    }


    // From HomeFragment.OnHomeFragmentInteractionListener
    override fun sendInfoToMainActivity(songName: String, artistName: String) {
        mMainPresenter.getLyricsFromServer(songName, artistName)
    }


    // From FavoritesFragment.OnFavsFragmentInteractionListener
    override fun displaySavedLyrics(songName: String, artistName: String) {
        mMainPresenter.getLyricsFromInternalStorage(songName, artistName, baseContext)
    }

    // From FavoritesFragment.OnFavsFragmentInteractionListener
    override fun eraseSavedLyrics(songName: String, artistName: String) {
        LyricsStorageManager.deleteLyricsFromStorage(
            File(baseContext.filesDir,
                songName.toLowerCase() + "+" + artistName.toLowerCase())
        )
    }

    // From MainView interface
    override fun displayLyrics(
        lyrics: Song,
        songName: String, artistName: String) {

        val intent = Intent(this, LyricsActivity::class.java)
        intent.putExtra("lyrics", lyrics.lyrics)
        intent.putExtra("song", songName)
        intent.putExtra("artist", artistName)

        startActivity(intent)
    }

    // From MainView interface
    override fun displayErrorMessage() {
        Toast.makeText(baseContext,
            "No Lyrics Available!", Toast.LENGTH_SHORT).show()
    }
}
