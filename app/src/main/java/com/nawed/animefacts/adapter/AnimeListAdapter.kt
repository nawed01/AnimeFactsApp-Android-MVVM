package com.nawed.animefacts.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.nawed.animefacts.AnimeFactFragment
import com.nawed.animefacts.R
import com.nawed.animefacts.models.DataX

class AnimeListAdapter(data: List<DataX>, private val parentFragmentManager: FragmentManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<DataX>

    init {
        items = data
    }
        override fun getItemCount(): Int {
            return items.size
        }


        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val model = items[position]
            val messageViewHolder = holder as MessageViewHolder
            messageViewHolder.animeName.text = model.anime_name
            Glide.with(messageViewHolder.animeImageView.context)
            .load(model.anime_img)
                .centerCrop()
                .into(messageViewHolder.animeImageView);
            messageViewHolder.itemView.setOnClickListener {
                val args = Bundle()
                args.putString("anime_name",model.anime_name)
                openAnimeFactFragment(args)
            }
        }

    private fun openAnimeFactFragment(args: Bundle?) {
        val animeFactFragment = AnimeFactFragment()
        animeFactFragment.arguments = args
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment_container, animeFactFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val rootCategoryView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_anime_detail, parent, false)
            return MessageViewHolder(rootCategoryView)
        }


        private inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val animeImageView: ImageView = itemView.findViewById<ImageView>(R.id.imageViewAnime)
            val animeName: TextView = itemView.findViewById<TextView>(R.id.textViewAnimeName)
        }
    }