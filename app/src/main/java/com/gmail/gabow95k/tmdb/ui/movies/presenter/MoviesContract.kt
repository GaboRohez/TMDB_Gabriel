package com.gmail.gabow95k.tmdb.ui.movies.presenter

import com.gmail.gabow95k.tmdb.base.BaseView
import com.gmail.gabow95k.tmdb.data.MoviesResponse
import com.gmail.gabow95k.tmdb.room.Movie
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response

interface MoviesContract {
    interface View : BaseView {
        fun setMovies(movies: MutableList<Movie>)
        fun noMovies()
    }

    interface Presenter {
        fun getMoviesFromAPI(page: Int)
    }

    interface Interactor {
        fun getMovies(page: Int): Single<Response<MoviesResponse>>
        fun insertList(list: MutableList<Movie>): Completable
    }
}