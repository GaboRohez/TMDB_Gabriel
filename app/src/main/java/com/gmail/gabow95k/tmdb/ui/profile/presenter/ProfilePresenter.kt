package com.gmail.gabow95k.tmdb.ui.profile.presenter

import com.gmail.gabow95k.tmdb.app.AppConfig
import com.gmail.gabow95k.tmdb.base.BasePresenter
import com.gmail.gabow95k.tmdb.data.PersonEntity
import com.gmail.gabow95k.tmdb.room.Characters
import com.gmail.gabow95k.tmdb.room.Person
import com.gmail.gabow95k.tmdb.ui.profile.interactor.ProfileInteractor

class ProfilePresenter(view: ProfileContract.View?, interactor: ProfileInteractor) :
    BasePresenter<ProfileContract.View?>(view),
    ProfileContract.Presenter {

    private val interactor: ProfileContract.Interactor

    init {
        this.interactor = interactor
    }

    override fun checkIfDataExistInDB() {
        addSubscription(interactor.isEmpty()
            ?.subscribe({
                if (it!!) {
                    getPersonFromAPI()
                } else {
                    getFromDB()
                }
            }) { throwable ->
                println(throwable.message.toString())
            })
    }

    private fun getFromDB() {
        addSubscription(interactor.getFromDB()
            .doOnSubscribe { view!!.showLoader() }
            .doAfterTerminate { view!!.hideLoader() }
            .subscribe({
                view?.setPerson(it)
            }) { throwable ->
                view?.showDialog(processError(throwable))
            })
    }

    override fun getPersonFromAPI() {
        addSubscription(interactor.getPerson()
            .subscribe({
                if (it.isSuccessful) {
                    if (it.body()?.results?.isEmpty()!!) {

                    } else {
                        insertInDB(it.body()?.results!![0])
                    }
                } else {
                    view?.showDialog(AppConfig.resourceManager?.getCommonError)
                }
            }) { throwable ->
                view?.showDialog(processError(throwable))
            })
    }

    private fun insertInDB(person: PersonEntity) {
        val characters: MutableList<Characters> = arrayListOf()

        person.characters.forEach {
            if (it.original_name.isNullOrEmpty() || it.original_name.isNullOrEmpty() || it.first_air_date.isNullOrEmpty()) {
                println("No available data")
            } else {
                characters.add(
                    Characters(
                        it.id,
                        it.backdrop_path,
                        it.first_air_date,
                        it.media_type,
                        it.original_name,
                        it.overview,
                        it.poster_path,
                        it.vote_average,
                        it.vote_count
                    )
                )
            }
        }.also {
            val item = Person(
                person.id,
                person.name,
                person.popularity,
                person.profile_path,
                person.known_for_department,
                characters
            )

            addSubscription(interactor.insert(item)
                ?.subscribe({
                    view?.setPerson(item)
                }) { throwable ->
                    println(throwable.message.toString())
                })
        }
    }
}