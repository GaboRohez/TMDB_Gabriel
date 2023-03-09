package com.gmail.gabow95k.tmdb.custom.filter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gmail.gabow95k.tmdb.R

class FilterOptionsAdapter(
    val context: Context,
    var filterOptions: List<FilterOption>,
    private val filterItemListener: FilterItemListener
) :
    RecyclerView.Adapter<FilterOptionViewHolder>(), FilterItemListener {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterOptionViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_filter_option, parent, false)

        return FilterOptionViewHolder(context, view, this)
    }

    override fun onBindViewHolder(holder: FilterOptionViewHolder, position: Int) {
        holder.bind(filterOptions[position])
    }

    override fun getItemCount(): Int {
        return filterOptions.size
    }

    override fun onFilterSelected(field: String?, value: String?) {
        if (field.isNullOrEmpty()) {
            for (filterOption in filterOptions) {
                filterOption.isSelected = false
            }
            notifyDataSetChanged()
        }
        filterItemListener.onFilterSelected(field, value)
    }
}