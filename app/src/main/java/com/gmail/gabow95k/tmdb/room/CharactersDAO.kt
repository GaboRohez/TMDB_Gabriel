package com.gmail.gabow95k.tmdb.room

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

@Dao
interface CharactersDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(characters: Characters): Completable

    @Query("SELECT * FROM characters_table")
    fun getCharacters(): Single<MutableList<Characters>>

}