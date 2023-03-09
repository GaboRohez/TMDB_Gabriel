package com.gmail.gabow95k.tmdb.ui.movies.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.gabow95k.tmdb.R
import com.gmail.gabow95k.tmdb.addFragment
import com.gmail.gabow95k.tmdb.app.AppConfig
import com.gmail.gabow95k.tmdb.base.BaseFragment
import com.gmail.gabow95k.tmdb.custom.GridSpacingItemDecoration
import com.gmail.gabow95k.tmdb.custom.filter.FilterItemListener
import com.gmail.gabow95k.tmdb.custom.filter.FilterOption
import com.gmail.gabow95k.tmdb.custom.filter.FilterOptionsView
import com.gmail.gabow95k.tmdb.databinding.FragmentMoviesBinding
import com.gmail.gabow95k.tmdb.room.AppDatabase
import com.gmail.gabow95k.tmdb.room.Movie
import com.gmail.gabow95k.tmdb.ui.detail_movie.DetailMovieFragment
import com.gmail.gabow95k.tmdb.ui.movies.adapter.MovieAdapter
import com.gmail.gabow95k.tmdb.ui.movies.interactor.MoviesInteractor
import com.gmail.gabow95k.tmdb.ui.movies.presenter.MoviesContract
import com.gmail.gabow95k.tmdb.ui.movies.presenter.MoviesPresenter
import java.util.*

class MoviesFragment : BaseFragment<MoviesContract.Presenter, FragmentMoviesBinding>(),
    MoviesContract.View,
    MovieAdapter.MovieIn, FilterItemListener {

    var page = 1
    private var readyToLoad = false
    private var enableToRequestAPI: Boolean = true
    private lateinit var filterOptionsView: FilterOptionsView
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
        setUpFilterOptions()
        presenter?.checkIfExistDataInDB(page)
        setUpEvents()
    }

    private fun setUpFilterOptions() {

        val filterOptions = listOf(
            FilterOption(
                "all",
                getString(R.string.all_filter),
                null
            ),
            FilterOption(
                "popular",
                getString(R.string.most_popular),
                null
            ),
            FilterOption(
                "rated",
                getString(R.string.top_rated),
                null
            ),
            FilterOption(
                "upcoming",
                getString(R.string.upcoming),
                null
            ),
            FilterOption("delete_filter", getString(R.string.delete_filter), null)
        )

        filterOptionsView = FilterOptionsView(requireContext(), filterOptions, this)
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
        binding?.btnFilter?.setOnClickListener {
            filterOptionsView.show()
        }

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
                if (dy > 0 && enableToRequestAPI) {
                    val visibleItemCount: Int = binding!!.recycler.layoutManager!!.childCount
                    val totalItemCount: Int = binding!!.recycler.layoutManager!!.itemCount
                    val pastVisibleItems =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    if (readyToLoad) {

                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            readyToLoad = false

                            page = (totalItemCount / 20) + 1
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

    override fun setMoviesFilter(movies: MutableList<Movie>) {
        list.clear()
        movies.let { list.addAll(it) }
        adapter.notifyDataSetChanged()
    }

    override fun noMovies() {
        list.clear()
        adapter.notifyDataSetChanged()

        binding?.tvNoAvailable?.visibility = View.VISIBLE
    }

    override fun onItemClick(movie: Movie) {
        addFragment(
            requireActivity().supportFragmentManager,
            DetailMovieFragment.newInstance(movie),
            R.id.contentFragment
        )
    }

    override fun onFilterSelected(field: String?, value: String?) {
        when (field) {
            "all" -> {
                enableToRequestAPI = true
                presenter?.getMoviesFromDB()
            }
            "popular" -> {
                enableToRequestAPI = false
                presenter?.getPopular()
            }
            "rated" -> {
                enableToRequestAPI = false
                presenter?.getRated()
            }
            "upcoming" -> {
                enableToRequestAPI = false
                presenter?.getUpcoming(Date())
            }
            "delete_filter" -> {
                enableToRequestAPI = true
                presenter?.getMoviesFromDB()
            }
        }
        filterOptionsView.hide()
    }
}