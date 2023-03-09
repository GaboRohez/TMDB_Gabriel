package com.gmail.gabow95k.tmdb.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class CharacterConverter {
    @TypeConverter
    fun fromCharacterList(characterLang: List<Characters?>?): String? {
        if (characterLang == null) {
            return null
        }
        val gson = Gson()
        val type: Type =
            object : TypeToken<List<Characters?>?>() {}.type
        return gson.toJson(characterLang, type)
    }

    @TypeConverter
    fun toCharacterList(characterString: String?): List<Characters?>? {
        if (characterString == null) {
            return null
        }
        val gson = Gson()
        val type: Type =
            object : TypeToken<List<Characters?>?>() {}.type
        return gson.fromJson(characterString, type)
    }
}