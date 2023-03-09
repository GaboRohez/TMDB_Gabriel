package com.gmail.gabow95k.tmdb.api

import com.gmail.gabow95k.tmdb.data.MoviesResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApiService {

    @GET("discover/movie?sort_by=popularity.desc")
    fun getPopularMovies(
        @Query("api_key") key: String,
        @Query("page") page: Int
    ): Single<Response<MoviesResponse>>

}