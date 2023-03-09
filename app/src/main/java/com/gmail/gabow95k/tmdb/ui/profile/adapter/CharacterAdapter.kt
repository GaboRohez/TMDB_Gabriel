package com.gmail.gabow95k.tmdb.ui.profile.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmail.gabow95k.tmdb.IMAGE_PATH
import com.gmail.gabow95k.tmdb.R
import com.gmail.gabow95k.tmdb.databinding.ItemCharacterBinding
import com.gmail.gabow95k.tmdb.room.Characters


class CharacterAdapter(var context: Context, var list: ArrayList<Characters>) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvName.text = list[position].name
        holder.binding.tvOverview.text = list[position].overview

        Glide.with(context)
            .load(IMAGE_PATH + list[position].posterPath)
            .placeholder(context.getDrawable(R.drawable.placeholder))
            .into(holder.binding.ivPoster)

        holder.binding.tvRate.text = list[position].voteAverage.toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(var binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}