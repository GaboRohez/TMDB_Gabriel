package com.gmail.gabow95k.tmdb.ui.movies.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.gabow95k.tmdb.R
import com.gmail.gabow95k.tmdb.app.AppConfig
import com.gmail.gabow95k.tmdb.base.BaseFragment
import com.gmail.gabow95k.tmdb.custom.GridSpacingItemDecoration
import com.gmail.gabow95k.tmdb.databinding.FragmentMoviesBinding
import com.gmail.gabow95k.tmdb.room.AppDatabase
import com.gmail.gabow95k.tmdb.room.Movie
import com.gmail.gabow95k.tmdb.ui.movies.adapter.MovieAdapter
import com.gmail.gabow95k.tmdb.ui.movies.interactor.MoviesInteractor
import com.gmail.gabow95k.tmdb.ui.movies.presenter.MoviesContract
import com.gmail.gabow95k.tmdb.ui.movies.presenter.MoviesPresenter


class MoviesFragment : BaseFragment<MoviesContract.Presenter, FragmentMoviesBinding>(),
    MoviesContract.View,
    MovieAdapter.MovieIn {

    var page = 1
    private var readyToLoad = false
    var interactor =
        MoviesInteractor(AppDatabase.getInstance(AppConfig.getAppContext()!!)!!.movieDAO()!!)
    private lateinit var adapter: MovieAdapter
    private lateinit var list: ArrayList<Movie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list = ArrayList()
        presenter = MoviesPresenter(this, interactor)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        presenter?.getMoviesFromAPI(page)
        setUpEvents()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpRecycler() {
        adapter = MovieAdapter(requireContext(), list, this)

        binding?.recycler?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.recycler?.setHasFixedSize(true)
        binding?.recycler?.adapter = adapter
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_layout_margin)
        binding?.recycler?.addItemDecoration(GridSpacingItemDecoration(3, spacingInPixels, true, 0))
        adapter.notifyDataSetChanged()
    }

    private fun setUpEvents() {
        binding?.etSearch?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }
        })

        binding!!.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount: Int = binding!!.recycler.layoutManager!!.childCount
                    val totalItemCount: Int = binding!!.recycler.layoutManager!!.itemCount
                    val pastVisibleItems =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    if (readyToLoad) {

                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            readyToLoad = false

                            page += 1
                            presenter?.getMoviesFromAPI(page)
                        }

                    }
                }
            }
        })
    }

    override fun setMovies(movies: MutableList<Movie>) {
        readyToLoad = true

        binding?.tvNoAvailable?.visibility = View.INVISIBLE

        movies.let { list.addAll(it) }
        adapter.notifyDataSetChanged()
    }

    override fun noMovies() {
        binding?.tvNoAvailable?.visibility = View.VISIBLE
    }

    override fun onItemClick(movie: Movie) {
        Toast.makeText(requireContext(), movie.title, Toast.LENGTH_LONG).show()
    }
}