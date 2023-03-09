package com.gmail.gabow95k.tmdb.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.gmail.gabow95k.tmdb.R

class AppConfig : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
        var resourceManager: AndroidResourceManager? = null

        fun getAppContext(): Context? {
            return context!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        resourceManager =
            AndroidResourceManager(
                resources
            )
    }

    class AndroidResourceManager(private val resources: Resources) {
        val stringAppName: String
            get() = resources.getString(R.string.app_name)
    }
}