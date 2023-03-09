package com.gmail.gabow95k.tmdb.custom.filter

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.gabow95k.tmdb.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class FilterOptionsView {
    private var filtersRecyclerView: RecyclerView
    private var filterOptionsAdapter: FilterOptionsAdapter
    private var dialog: BottomSheetDialog

    constructor(
        context: Context,
        filterOptions: List<FilterOption>,
        filterItemListener: FilterItemListener
    ) {

        dialog = BottomSheetDialog(context, R.style.BottomSheetDialog)

        val filterView = LayoutInflater.from(context).inflate(R.layout.view_filters, null)

        filtersRecyclerView = filterView.findViewById(R.id.filtersRecyclerView)

        filterOptionsAdapter = FilterOptionsAdapter(context, filterOptions, filterItemListener)

        filtersRecyclerView.layoutManager = LinearLayoutManager(context)
        filtersRecyclerView.setHasFixedSize(true)
        filtersRecyclerView.adapter = filterOptionsAdapter

        dialog.setContentView(filterView)
    }

    fun show() {
        dialog.show()
    }

    fun hide() {
        dialog.hide()
    }

}