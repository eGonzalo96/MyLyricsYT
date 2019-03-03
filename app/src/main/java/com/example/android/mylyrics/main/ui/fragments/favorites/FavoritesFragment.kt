package com.example.android.mylyrics.main.ui.fragments.favorites

import android.os.Bundle
import android.os.storage.StorageManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.android.mylyrics.R
import com.example.android.mylyrics.main.ui.fragments.favorites.entities.LyricsInformation
import com.example.android.mylyrics.main.ui.fragments.favorites.entities.LyricsInformationAdapter
import com.example.android.mylyrics.storagemanager.LyricsStorageManager
import kotlinx.android.synthetic.main.fragment_main_activity_favorites.view.*

class FavoritesFragment : Fragment(),
    FavoritesView, LyricsInformationAdapter.OnItemClickListener {

    val FAVORITES_FRAGMENT_ID = "favorites_fragment"
    val mPresenter = FavoritesPresenter(this)
    lateinit var mRootView: View
    lateinit var mRecyclerViewAdapter: LyricsInformationAdapter
    lateinit var mLyricsInformationList: ArrayList<LyricsInformation>
    lateinit var mRecyclerView: RecyclerView
    lateinit var mFragmentInteractionListener: OnFavsFragmentInteractionListener


    interface OnFavsFragmentInteractionListener {
        fun displaySavedLyrics(songName: String,
                               artistName: String)
        fun eraseSavedLyrics(songName: String,
                            artistName: String)
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater
            .inflate(
                R.layout.fragment_main_activity_favorites,
                container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        view
            .favoritesFragment_favoritesList
            .layoutManager = LinearLayoutManager(view.context)

        mRootView = view
        mLyricsInformationList = mPresenter.getSavedLyricsList(view.context)
        mRecyclerViewAdapter = LyricsInformationAdapter(
            mLyricsInformationList, this
        )

        view
            .favoritesFragment_favoritesList
            .adapter = mRecyclerViewAdapter

        mRecyclerView = view.favoritesFragment_favoritesList
    }


    override fun getLyricsInformationToFragment(lyrics: LyricsInformation) {
        mFragmentInteractionListener
            .displaySavedLyrics(lyrics.songName, lyrics.artistName)

    }

    override fun onResume() {
        super.onResume()
        val newLyricsList = mPresenter.getSavedLyricsList(mRootView.context)
        var erasedLyricsPosition = 0
        for(i in mLyricsInformationList) {
            if(!newLyricsList.contains(i)) {
                erasedLyricsPosition = mLyricsInformationList.indexOf(i)
                removeItemFromRecyclerView(erasedLyricsPosition)
            }
        }
    }

    override fun removeItemFromRecyclerView(position: Int) {
        val lyricsInfo = mLyricsInformationList.removeAt(position)
        mRecyclerViewAdapter.notifyItemRemoved(position)
        mRecyclerViewAdapter.notifyItemRangeChanged(position, mLyricsInformationList.size)

        mFragmentInteractionListener
            .eraseSavedLyrics(lyricsInfo.songName, lyricsInfo.artistName)
    }
}