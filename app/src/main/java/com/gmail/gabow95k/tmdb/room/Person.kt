package com.gmail.gabow95k.tmdb.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.gabow95k.tmdb.TABLE_NAME_PERSON
import kotlinx.parcelize.Parcelize
import org.jetbrains.annotations.NotNull
import java.util.*

@Parcelize
@Entity(tableName = TABLE_NAME_PERSON)
data class Person(
    @ColumnInfo(name = "last_update") var lastUpdate: Date,
    @ColumnInfo(name = "idTMDB") var idTMDB: Int,
    @ColumnInfo(name = "name") @NotNull var name: String,
    @ColumnInfo(name = "popularity") @NotNull var popularity: Double,
    @ColumnInfo(name = "profile_path") var profilePath: String,
    @ColumnInfo(name = "known_for_department") var department: String,
    @ColumnInfo(name = "characters") var characters: MutableList<Characters>
) : Parcelable {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int = 1
}
