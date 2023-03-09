package com.gmail.gabow95k.tmdb.ui.movies.presenter

import com.gmail.gabow95k.tmdb.base.BaseView
import com.gmail.gabow95k.tmdb.data.MoviesResponse
import com.gmail.gabow95k.tmdb.room.Movie
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response
import java.util.*

interface MoviesContract {
    interface View : BaseView {
        fun setMovies(movies: MutableList<Movie>)
        fun noMovies()
        fun setMoviesFilter(movies: MutableList<Movie>)
    }

    interface Presenter {
        fun getMoviesFromAPI(page: Int)
        fun checkIfExistDataInDB(page: Int)
        fun getMoviesFromDB()
        fun getPopular()
        fun getRated()
        fun getUpcoming(date: Date)
    }

    interface Interactor {
        fun getMovies(page: Int): Single<Response<MoviesResponse>>
        fun insertList(list: MutableList<Movie>): Completable
        fun tableIsEmpty(): Single<Boolean>
        fun getFromDB(): Single<MutableList<Movie>>
        fun getPopular(): Single<MutableList<Movie>>
        fun getRated(): Single<MutableList<Movie>>
        fun getUpcoming(date: Date): Single<MutableList<Movie>>
    }
}