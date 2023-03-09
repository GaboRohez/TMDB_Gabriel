package com.gmail.gabow95k.tmdb.api

import com.gmail.gabow95k.tmdb.data.MoviesResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET

interface TMDBApiService {

    @GET("verb.php")
    fun getPopularMovies(): Single<Response<MoviesResponse>>

}