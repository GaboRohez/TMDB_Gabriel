package com.gmail.gabow95k.tmdb.ui.profile.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmail.gabow95k.tmdb.IMAGE_PATH
import com.gmail.gabow95k.tmdb.R
import com.gmail.gabow95k.tmdb.databinding.ItemCharacterBinding
import com.gmail.gabow95k.tmdb.room.Characters
import com.gmail.gabow95k.tmdb.room.Movie
import java.text.SimpleDateFormat


class CharacterAdapter(
    var context: Context,
    var list: ArrayList<Characters>,
    var listener: CharacterIn
) :
    RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    interface CharacterIn {
        fun onCharacterClick(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvName.text = list[position].name
        holder.binding.tvOverview.text = list[position].overview

        Glide.with(context)
            .load(IMAGE_PATH + list[position].posterPath)
            .placeholder(context.getDrawable(R.drawable.placeholder))
            .into(holder.binding.ivPoster)

        holder.binding.tvRate.text = list[position].voteAverage.toString()

        holder.binding.content.setOnClickListener {
            val movie = Movie(
                list[position].id,
                list[position].name,
                list[position].overview,
                12.3,
                list[position].posterPath,
                list[position].backdropPath,
                SimpleDateFormat("yyyy-mm-dd").parse(list[position].firstAirDate)!!,
                list[position].voteAverage,
                list[position].voteCount
            )
            listener.onCharacterClick(movie)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(var binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {
    }
}