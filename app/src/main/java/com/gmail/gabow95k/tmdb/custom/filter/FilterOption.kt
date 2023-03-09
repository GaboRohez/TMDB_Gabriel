package com.gmail.gabow95k.tmdb.custom.filter

data class FilterOption(
    val field: String,
    val label: String,
    val value: String?,
    var isSelected: Boolean = false,
    var isOpen: Boolean = false,
    var subOptionSelected: Int = -1
)