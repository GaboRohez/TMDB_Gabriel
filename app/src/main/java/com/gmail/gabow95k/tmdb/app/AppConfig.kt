package com.gmail.gabow95k.tmdb.app

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.gmail.gabow95k.tmdb.R
import com.gmail.gabow95k.tmdb.room.AppDatabase

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
        AppDatabase.getInstance(context!!)
        resourceManager =
            AndroidResourceManager(
                resources
            )
    }

    class AndroidResourceManager(private val resources: Resources) {
        val stringAppName: String
            get() = resources.getString(R.string.app_name)

        val getCommonError: String
            get() = resources.getString(R.string.common_error)
    }
}