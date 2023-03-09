package com.gmail.gabow95k.tmdb.ui.movies.presenter

import com.gmail.gabow95k.tmdb.app.AppConfig
import com.gmail.gabow95k.tmdb.base.BasePresenter
import com.gmail.gabow95k.tmdb.ui.movies.interactor.MoviesInteractor

class MoviesPresenter(view: MoviesContract.View?, interactor: MoviesInteractor) :
    BasePresenter<MoviesContract.View?>(view),
    MoviesContract.Presenter {

    private val interactor: MoviesContract.Interactor

    init {
        this.interactor = interactor
    }

    override fun getMovies(page: Int) {
        addSubscription(interactor.getMovies(page)
            .doOnSubscribe { view!!.showLoader() }
            .doAfterTerminate { view!!.hideLoader() }
            .subscribe({
                if (it.isSuccessful) {
                    if (it.body()?.results?.isNotEmpty()!!) {
                        view?.setMovies(it.body()!!.results)
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
}