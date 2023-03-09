package com.gmail.gabow95k.tmdb.ui.detail_movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.gmail.gabow95k.tmdb.IMAGE_PATH
import com.gmail.gabow95k.tmdb.R
import com.gmail.gabow95k.tmdb.databinding.FragmentDetailMovieBinding
import com.gmail.gabow95k.tmdb.room.Movie
import java.text.SimpleDateFormat

private const val ARG_PARAM = "movie"

class DetailMovieFragment : Fragment() {

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!
    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = it.getParcelable(ARG_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireContext())
            .load(IMAGE_PATH + movie?.backdropPath)
            .placeholder(requireContext().getDrawable(R.drawable.placeholder))
            .into(binding.ivFilm)
        binding.tvRate.text = movie?.voteAverage.toString()
        binding.tvTitle.text = movie?.title
        binding.tvReleaseDate.text = String.format(
            requireActivity().getString(R.string.release_date),
            SimpleDateFormat("MMMM dd, yyyy").format(movie!!.releaseDate)
        )

        binding.tvOverview.text = movie?.overview
    }

    companion object {
        @JvmStatic
        fun newInstance(movie: Movie) =
            DetailMovieFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM, movie)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}