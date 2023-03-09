package com.gmail.gabow95k.tmdb.ui.movies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gmail.gabow95k.tmdb.IMAGE_PATH
import com.gmail.gabow95k.tmdb.data.MovieEntity
import com.gmail.gabow95k.tmdb.databinding.ItemMovieBinding

class MovieAdapter(var context: Context, var list: ArrayList<MovieEntity>, var listener: MovieIn) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>(), Filterable {

    var list1: ArrayList<MovieEntity> = list

    interface MovieIn {
        fun onItemClick(movie: MovieEntity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(IMAGE_PATH + list[position].poster_path)
            .into(holder.binding.ivMovie)

        holder.binding.tvRate.text = list[position].vote_average.toString()
        holder.binding.content.setOnClickListener {
            listener.onItemClick(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(var binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                if (charString.isEmpty()) {
                    list = list1
                } else {
                    val filterList: ArrayList<MovieEntity> =
                        java.util.ArrayList<MovieEntity>()
                    for (data in list1) {
                        if (data.original_title?.toLowerCase()!!
                                .contains(charString.toLowerCase())
                        ) {
                            filterList.add(data)
                        }
                    }
                    list = filterList
                }
                val filterResults = FilterResults()
                filterResults.values = list
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                list = results?.values as ArrayList<MovieEntity>
                notifyDataSetChanged()
            }
        }
    }
}