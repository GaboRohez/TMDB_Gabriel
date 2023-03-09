package com.gmail.gabow95k.tmdb.custom.filter

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gmail.gabow95k.tmdb.R

class FilterOptionViewHolder(
    val context: Context,
    view: View,
    private val filterItemListener: FilterOptionsAdapter
) : RecyclerView.ViewHolder(view) {
    private val filterOptionLabelTextView: TextView
    private val filterOptionItemContentView: View
    private lateinit var filterOption: FilterOption

    init {
        filterOptionLabelTextView = view.findViewById(R.id.filterOptionLabelTextView)
        filterOptionItemContentView = view.findViewById(R.id.filterOptionItemContentView)
        filterOptionItemContentView.setOnClickListener {
            filterOption.isOpen = !filterOption.isOpen
            if (!filterOption.field.isNullOrEmpty()) {
                filterOption.isSelected = !filterOption.isSelected
                if (filterOption.isSelected) {
                    filterItemListener.onFilterSelected(filterOption.field, filterOption.value)
                } else {
                    filterItemListener.onFilterSelected(filterOption.field, "")
                }
            } else {
                filterItemListener.onFilterSelected("", null)
            }
        }
    }

    fun bind(filterOption: FilterOption) {
        this.filterOption = filterOption
        filterOptionLabelTextView.text = this.filterOption.label
    }
}