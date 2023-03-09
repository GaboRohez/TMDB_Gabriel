package com.gmail.gabow95k.tmdb.ui.movies.presenter

import android.annotation.SuppressLint
import com.gmail.gabow95k.tmdb.app.AppConfig
import com.gmail.gabow95k.tmdb.base.BasePresenter
import com.gmail.gabow95k.tmdb.data.MovieEntity
import com.gmail.gabow95k.tmdb.room.Movie
import com.gmail.gabow95k.tmdb.ui.movies.interactor.MoviesInteractor
import java.text.SimpleDateFormat

class MoviesPresenter(view: MoviesContract.View?, interactor: MoviesInteractor) :
    BasePresenter<MoviesContract.View?>(view),
    MoviesContract.Presenter {

    private val interactor: MoviesContract.Interactor

    init {
        this.interactor = interactor
    }

    override fun getMoviesFromAPI(page: Int) {
        addSubscription(interactor.getMovies(page)
            .doOnSubscribe { view!!.showLoader() }
            .doAfterTerminate { view!!.hideLoader() }
            .subscribe({
                if (it.isSuccessful) {
                    if (it.body()?.results?.isNotEmpty()!!) {
                        insertInDB(it.body()!!.results)
                    } else {
                        view?.noMovies()
                    }
                } else {
                    view?.showDialog(AppConfig.resourceManager?.getCommonError)
                }
            }) { throwable ->
                view?.showDialog(processError(throwable))
            })
    }

    @SuppressLint("SimpleDateFormat")
    private fun insertInDB(movies: List<MovieEntity>) {

        val list: MutableList<Movie> = arrayListOf()

        movies.forEach {
            list.add(
                Movie(
                    it.id,
                    it.original_title,
                    it.overview,
                    it.popularity,
                    if (it.poster_path.isNullOrEmpty()) "" else it.poster_path,
                    if (it.backdrop_path.isNullOrEmpty()) "" else it.backdrop_path,
                    if (it.release_date.isNullOrEmpty()) SimpleDateFormat("yyyy-mm-dd").parse("0001-01-01")!! else SimpleDateFormat(
                        "yyyy-mm-dd"
                    ).parse(it.release_date)!!,
                    it.vote_average,
                    it.vote_count
                )
            )
        }.also {
            addSubscription(interactor.insertList(list)
                ?.subscribe({
                    view?.setMovies(list)
                }) { throwable ->
                    println(throwable.message.toString())
                })
        }
    }
}