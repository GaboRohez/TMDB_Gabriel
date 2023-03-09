package com.gmail.gabow95k.tmdb.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gmail.gabow95k.converter.DateConverter
import com.gmail.gabow95k.tmdb.DATABASE_NAME

@TypeConverters(DateConverter::class, CharacterConverter::class)
@Database(entities = [Movie::class, Characters::class, Person::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDAO(): MovieDAO?
    abstract fun characterDAO(): CharactersDAO?
    abstract fun personDAO(): PersonDAO?

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, DATABASE_NAME
                ).build()
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}