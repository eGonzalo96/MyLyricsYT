package com.example.android.mylyrics.main.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.mylyrics.R
import kotlinx.android.synthetic.main.fragment_main_activity_home.view.*

class HomeFragment: Fragment() {

    val HOME_FRAGMENT_ID = "home_fragment"
    lateinit var mHomeListener: OnHomeFragmentInteractionListener


    interface OnHomeFragmentInteractionListener {
        fun sendInfoToMainActivity(songName: String, artistName: String)
    }


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       return inflater.inflate(R.layout.fragment_main_activity_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.homeFragment_button_submitLyrics
            .setOnClickListener { v ->
                val songName = view.homeFragment_editText_song.text.toString()
                val artistName = view.homeFragment_editText_artist.text.toString()

                mHomeListener.sendInfoToMainActivity(songName, artistName)
            }

    }

}