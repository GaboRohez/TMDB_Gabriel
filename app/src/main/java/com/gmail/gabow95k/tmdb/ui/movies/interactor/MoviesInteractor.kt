package com.gmail.gabow95k.tmdb.ui.movies.interactor

import com.gmail.gabow95k.tmdb.BuildConfig
import com.gmail.gabow95k.tmdb.api.RetrofitClient
import com.gmail.gabow95k.tmdb.api.TMDBApiService
import com.gmail.gabow95k.tmdb.data.MoviesResponse
import com.gmail.gabow95k.tmdb.room.Movie
import com.gmail.gabow95k.tmdb.room.MovieDAO
import com.gmail.gabow95k.tmdb.ui.movies.presenter.MoviesContract
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import java.util.*

class MoviesInteractor(var movieDAO: MovieDAO) : MoviesContract.Interactor {
    override fun getMovies(page: Int): Single<Response<MoviesResponse>> {
        return RetrofitClient.instance
            .create(TMDBApiService::class.java)
            .getPopularMovies(BuildConfig.API_KEY, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun insertList(list: MutableList<Movie>): Completable {
        return movieDAO.insertList(list)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun tableIsEmpty(): Single<Boolean> {
        return movieDAO.isEmpty()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getFromDB(): Single<MutableList<Movie>> {
        return movieDAO.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getPopular(): Single<MutableList<Movie>> {
        return movieDAO.getPopulars()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getRated(): Single<MutableList<Movie>> {
        return movieDAO.getRated()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getUpcoming(date: Date): Single<MutableList<Movie>> {
        return movieDAO.getUpcoming(date)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}