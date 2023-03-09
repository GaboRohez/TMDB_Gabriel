package com.gmail.gabow95k.tmdb.ui.movies.interactor

import com.gmail.gabow95k.tmdb.BuildConfig
import com.gmail.gabow95k.tmdb.api.RetrofitClient
import com.gmail.gabow95k.tmdb.api.TMDBApiService
import com.gmail.gabow95k.tmdb.data.MoviesResponse
import com.gmail.gabow95k.tmdb.ui.movies.presenter.MoviesContract
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MoviesInteractor : MoviesContract.Interactor {
    override fun getMovies(page: Int): Single<Response<MoviesResponse>> {
        return RetrofitClient.instance
            .create(TMDBApiService::class.java)
            .getPopularMovies(BuildConfig.API_KEY, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}