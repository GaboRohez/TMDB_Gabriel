package com.gmail.gabow95k.tmdb.ui.movies.presenter

import com.gmail.gabow95k.tmdb.base.BaseView
import com.gmail.gabow95k.tmdb.data.MovieEntity
import com.gmail.gabow95k.tmdb.data.MoviesResponse
import io.reactivex.Single
import retrofit2.Response

interface MoviesContract {
    interface View : BaseView {
        fun setMovies(movies: List<MovieEntity>)
        fun noMovies()
    }

    interface Presenter {
        fun getMovies(page: Int)
    }

    interface Interactor {
        fun getMovies(page: Int): Single<Response<MoviesResponse>>
    }
}