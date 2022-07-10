package com.nawed.animefacts.adapter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nawed.animefacts.AnimeFactFragment
import com.nawed.animefacts.R
import com.nawed.animefacts.models.Data
import com.nawed.animefacts.models.DataX

class AnimeFactListAdapter(data: List<Data?>?, private val parentFragmentManager: FragmentManager) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Data?>

    init {
        items = data!!
    }
        override fun getItemCount(): Int {
            return items.size
        }


        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val model = items[position]
            val messageViewHolder = holder as MessageViewHolder
            messageViewHolder.animeFact.text = model?.fact
        }
    
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val rootCategoryView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_anime_fact, parent, false)
            return MessageViewHolder(rootCategoryView)
        }


        private inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val animeFact: TextView = itemView.findViewById<TextView>(R.id.textViewAnimeFact)
        }
    }