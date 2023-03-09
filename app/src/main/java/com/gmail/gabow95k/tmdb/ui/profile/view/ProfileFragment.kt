package com.gmail.gabow95k.tmdb.ui.profile.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gmail.gabow95k.tmdb.IMAGE_PATH
import com.gmail.gabow95k.tmdb.R
import com.gmail.gabow95k.tmdb.addFragment
import com.gmail.gabow95k.tmdb.app.AppConfig
import com.gmail.gabow95k.tmdb.base.BaseFragment
import com.gmail.gabow95k.tmdb.databinding.FragmentProfileBinding
import com.gmail.gabow95k.tmdb.room.AppDatabase
import com.gmail.gabow95k.tmdb.room.Characters
import com.gmail.gabow95k.tmdb.room.Movie
import com.gmail.gabow95k.tmdb.room.Person
import com.gmail.gabow95k.tmdb.ui.detail_movie.DetailMovieFragment
import com.gmail.gabow95k.tmdb.ui.profile.adapter.CharacterAdapter
import com.gmail.gabow95k.tmdb.ui.profile.interactor.ProfileInteractor
import com.gmail.gabow95k.tmdb.ui.profile.presenter.ProfileContract
import com.gmail.gabow95k.tmdb.ui.profile.presenter.ProfilePresenter

class ProfileFragment : BaseFragment<ProfileContract.Presenter, FragmentProfileBinding>(),
    ProfileContract.View, CharacterAdapter.CharacterIn {

    private lateinit var adapter: CharacterAdapter
    private lateinit var list: ArrayList<Characters>
    var interactor =
        ProfileInteractor(AppDatabase.getInstance(AppConfig.getAppContext()!!)!!.personDAO()!!)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list = ArrayList()
        presenter = ProfilePresenter(this, interactor)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        presenter?.checkIfDataExistInDB()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpRecycler() {
        adapter = CharacterAdapter(requireContext(), list, this)

        binding?.recycler?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recycler?.setHasFixedSize(true)
        binding?.recycler?.adapter = adapter
        binding?.recycler?.isNestedScrollingEnabled = false
        adapter.notifyDataSetChanged()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun setPerson(person: Person) {
        binding?.tvDepartment?.text = person.department
        binding?.tvName?.text = person.name
        Glide.with(requireContext())
            .load(IMAGE_PATH + person.profilePath)
            .placeholder(requireContext().getDrawable(R.drawable.placeholder))
            .into(binding?.ivPerson!!)

        list.clear()
        person.characters.let { list.addAll(it) }

        adapter.notifyDataSetChanged()
    }

    override fun onCharacterClick(movie: Movie) {
        addFragment(
            requireActivity().supportFragmentManager,
            DetailMovieFragment.newInstance(movie),
            R.id.contentFragment
        )
    }
}