package com.gmail.gabow95k.tmdb.ui.profile.presenter

import com.gmail.gabow95k.tmdb.base.BaseView
import com.gmail.gabow95k.tmdb.data.PersonResponse
import com.gmail.gabow95k.tmdb.room.Person
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Response

interface ProfileContract {
    interface View : BaseView {
        fun setPerson(person: Person)
    }

    interface Presenter {
        fun getPersonFromAPI()
        fun checkIfDataExistInDB()
    }

    interface Interactor {
        fun getPerson(): Single<Response<PersonResponse>>
        fun insert(item: Person): Completable
        fun isEmpty(): Single<Boolean>
        fun getFromDB(): Single<Person>
    }
}