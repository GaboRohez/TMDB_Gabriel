package com.gmail.gabow95k.tmdb.room

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface MovieDAO {
    @Query("SELECT * FROM movies_table ORDER BY title")
    fun getAll(): Single<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Movie): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(products: List<Movie>): Completable

}