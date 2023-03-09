package com.gmail.gabow95k.tmdb.ui.profile.interactor

import com.gmail.gabow95k.tmdb.BuildConfig
import com.gmail.gabow95k.tmdb.api.RetrofitClient
import com.gmail.gabow95k.tmdb.api.TMDBApiService
import com.gmail.gabow95k.tmdb.data.PersonResponse
import com.gmail.gabow95k.tmdb.room.Person
import com.gmail.gabow95k.tmdb.room.PersonDAO
import com.gmail.gabow95k.tmdb.ui.profile.presenter.ProfileContract
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class ProfileInteractor(var personDAO: PersonDAO) : ProfileContract.Interactor {

    override fun isEmpty(): Single<Boolean> {
        return personDAO.isEmpty()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getPerson(): Single<Response<PersonResponse>> {
        return RetrofitClient.instance
            .create(TMDBApiService::class.java)
            .getPopularPerson(BuildConfig.API_KEY)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun insert(item: Person): Completable {
        return personDAO.insert(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getFromDB(): Single<Person> {
        return personDAO.getPerson()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}