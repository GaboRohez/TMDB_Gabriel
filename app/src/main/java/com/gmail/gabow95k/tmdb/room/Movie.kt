package com.gmail.gabow95k.tmdb.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.gabow95k.tmdb.TABLE_NAME_MOVIES
import kotlinx.parcelize.Parcelize
import org.jetbrains.annotations.NotNull
import java.util.*

@Parcelize
@Entity(tableName = TABLE_NAME_MOVIES)
data class Movie(
    @ColumnInfo(name = "idTMDB") var idTMDB: Int,
    @ColumnInfo(name = "title") @NotNull var title: String,
    @ColumnInfo(name = "overview") @NotNull var overview: String,
    @ColumnInfo(name = "popularity") @NotNull var popularity: Double,
    @ColumnInfo(name = "poster_path") var posterPath: String,
    @ColumnInfo(name = "backdrop_path") var backdropPath: String,
    @ColumnInfo(name = "release_date") @NotNull var releaseDate: Date,
    @ColumnInfo(name = "vote_average") @NotNull var voteAverage: Double,
    @ColumnInfo(name = "vote_count") @NotNull var voteCount: Int
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
