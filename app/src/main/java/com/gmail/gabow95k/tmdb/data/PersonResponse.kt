package com.gmail.gabow95k.tmdb.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PersonResponse(
    @Expose
    @SerializedName("page")
    val page: Int,
    @Expose
    @SerializedName("results")
    val results: List<PersonEntity>
)

data class PersonEntity(
    @Expose
    @SerializedName("adult")
    var adult: Boolean,
    @Expose
    @SerializedName("gender")
    var gender: Int,
    @Expose
    @SerializedName("id")
    var id: Int,
    @Expose
    @SerializedName("known_for")
    var characters: List<CharactersEntity>,
    @Expose
    @SerializedName("known_for_department")
    val known_for_department: String,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("popularity")
    val popularity: Double,
    @Expose
    @SerializedName("profile_path")
    val profile_path: String
)

data class CharactersEntity(
    @Expose
    @SerializedName("backdrop_path")
    val backdrop_path: String,
    @Expose
    @SerializedName("first_air_date")
    val first_air_date: String,
    @Expose
    @SerializedName("release_date")
    val release_date: String,
    @Expose
    @SerializedName("genre_ids")
    var genre_ids: List<Integer>,
    @Expose
    @SerializedName("id")
    var id: Int,
    @Expose
    @SerializedName("media_type")
    val media_type: String,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("origin_country")
    var origin_country: List<String>,
    @Expose
    @SerializedName("original_language")
    val original_language: String,
    @Expose
    @SerializedName("original_name")
    val original_name: String,
    @Expose
    @SerializedName("original_title")
    val original_title: String,
    @Expose
    @SerializedName("overview")
    val overview: String,
    @Expose
    @SerializedName("poster_path")
    val poster_path: String,
    @Expose
    @SerializedName("vote_average")
    val vote_average: Double,
    @Expose
    @SerializedName("vote_count")
    var vote_count: Int
)
