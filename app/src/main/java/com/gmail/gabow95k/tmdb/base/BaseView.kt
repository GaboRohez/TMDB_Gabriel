package com.gmail.gabow95k.tmdb.base

interface BaseView {
    fun showLoader()
    fun hideLoader()
    fun showDialog(message: String?)
    fun showDialog(resourceId: Int)
}