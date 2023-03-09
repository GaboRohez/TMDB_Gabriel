package com.gmail.gabow95k.tmdb.room

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

@Dao
interface PersonDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(person: Person): Completable

    @Query("SELECT * FROM person_table LIMIT 1")
    fun getPerson(): Single<Person>

    @Query("SELECT (SELECT COUNT(*) FROM person_table) == 0")
    fun isEmpty(): Single<Boolean>
}