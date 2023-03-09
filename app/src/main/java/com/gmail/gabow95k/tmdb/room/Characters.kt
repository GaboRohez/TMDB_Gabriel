package com.gmail.gabow95k.tmdb.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.gabow95k.tmdb.TABLE_NAME_CHARACTERS
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME_CHARACTERS)
data class Characters(
    @ColumnInfo(name = "idTMDB") var idTMDB: Int,
    @ColumnInfo(name = "backdrop_path") var backdropPath: String,
    @ColumnInfo(name = "first_air_date") var firstAirDate: String,
    @ColumnInfo(name = "media_type") var mediaType: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "overview") var overview: String,
    @ColumnInfo(name = "poster_path") var posterPath: String,
    @ColumnInfo(name = "vote_average") var voteAverage: Double,
    @ColumnInfo(name = "vote_count") var voteCount: Int,
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0
}
