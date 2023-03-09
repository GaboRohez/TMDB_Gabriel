package com.gmail.gabow95k.tmdb.room

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

@Dao
interface MovieDAO {
    @Query("SELECT * FROM movies_table ORDER BY title")
    fun getAll(): Single<MutableList<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(product: Movie): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(products: List<Movie>): Completable

    @Query("SELECT (SELECT COUNT(*) FROM movies_table) == 0")
    fun isEmpty(): Single<Boolean>

    @Query("SELECT * FROM movies_table ORDER BY popularity DESC")
    fun getPopulars(): Single<MutableList<Movie>>

    @Query("SELECT * FROM movies_table ORDER BY vote_count DESC")
    fun getRated(): Single<MutableList<Movie>>

    @Query("SELECT * FROM movies_table WHERE release_date >=:date")
    fun getUpcoming(date: Date): Single<MutableList<Movie>>
}