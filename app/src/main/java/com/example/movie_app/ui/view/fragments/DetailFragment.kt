package com.example.movie_app.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.example.movie_app.R
import com.example.movie_app.databinding.FragmentDetailsBinding
import com.example.movie_app.ui.viewmodel.MovieViewModel
import com.squareup.picasso.Picasso

class DetailFragment: Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private var idMovie: Int = 0
    private val movieViewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        idMovie = this.arguments?.getInt("id") ?: 0

        binding = FragmentDetailsBinding.inflate(inflater, container, false)

        movieViewModel.getMovieById(idMovie)
        movieViewModel.movieDetail.observe( viewLifecycleOwner) {

            val dateReleased = resources.getString(R.string.date_released)+": " + it.release_date
            val originalLanguage = resources.getString(R.string.originalLanguage)+": " + it.originalLanguage
            val popularity = resources.getString(R.string.popularity)+": " + it.popularity
            val voteAverage = resources.getString(R.string.voteAverage)+": " + it.voteAverage
            val genres = resources.getString(R.string.genres)+": " + it.genres

            binding.name.text = it.originalTitle
            binding.overview.text = it.overview
            binding.dateReleased.text = dateReleased
            binding.languageOriginal.text = originalLanguage
            binding.popularity.text = popularity
            binding.voteAverage.text = voteAverage
            binding.genres.text = genres

            val imageURL = "https://image.tmdb.org/t/p/original${it.backdropPath}"
            Picasso.get().load(imageURL).error(R.drawable.ic_without_image).placeholder(R.drawable.progress_animation).into(binding.backgroundImage)
        }

        movieViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressCircular.isVisible = it
        }

        binding.buttonScore.setOnClickListener {
            movieViewModel.setScore(
                binding.textScore.text.toString()
            )
        }

        movieViewModel.toastRate.observe(viewLifecycleOwner) {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }

        movieViewModel.toastDetail.observe(viewLifecycleOwner) {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        binding.textScore.text.clear()
    }
}