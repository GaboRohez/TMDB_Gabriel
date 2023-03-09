package com.gmail.gabow95k.tmdb.base

import com.gmail.gabow95k.tmdb.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.net.SocketTimeoutException

open class BasePresenter<T>(view: T) {

    var view: T? = null
    private val compositeDisposable: CompositeDisposable
    val isViewAttached: Boolean
        get() = view != null

    fun addSubscription(disposable: Disposable?) {
        compositeDisposable.add(disposable!!)
    }

    fun detachView() {
        compositeDisposable.clear()
        view = null
    }

    init {
        this.view = view
        compositeDisposable = CompositeDisposable()
    }

    protected open fun processError(throwable: Throwable): Int {
        throwable.printStackTrace()
        return if (throwable is SocketTimeoutException) {
            R.string.retrofit_timeout
        } else {
            R.string.retrofit_failure
        }
    }
}