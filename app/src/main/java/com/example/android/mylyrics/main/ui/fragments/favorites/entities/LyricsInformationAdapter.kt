package com.example.android.mylyrics.main.ui.fragments.favorites.entities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.mylyrics.R
import kotlinx.android.synthetic.main.favorites_item.view.*

class LyricsInformationAdapter(val mLyrics: ArrayList<LyricsInformation>,
                               val mOnItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<LyricsInformationAdapter.LyricsViewHolder>() {


    interface OnItemClickListener {
        fun getLyricsInformationToFragment(
            lyrics: LyricsInformation
        )

        fun removeItemFromRecyclerView(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LyricsViewHolder {
        val rootView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.favorites_item,
                parent, false)

        return LyricsViewHolder(
            rootView
        )
    }

    override fun onBindViewHolder(holder: LyricsViewHolder, position: Int) {
        holder.bind(mLyrics.get(position), position)

        holder.view.recycler_songName.setOnClickListener { v ->
            mOnItemClickListener
                .getLyricsInformationToFragment(mLyrics[position])
        }

        holder.view.recycler_delete.setOnClickListener{ v ->
            mOnItemClickListener
                .removeItemFromRecyclerView(position)
        }
    }


    override fun getItemCount(): Int {
        return mLyrics.size
    }


    class LyricsViewHolder(val view: View): RecyclerView.ViewHolder(view) {


        fun bind(lyric: LyricsInformation, position: Int) {
            view.recycler_songName.text = lyric.songName
            view.recycler_artistName.text = lyric.artistName
        }
    }

}