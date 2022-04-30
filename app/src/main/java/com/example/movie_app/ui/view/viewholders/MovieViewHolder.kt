package com.example.movie_app.ui.view.viewholders

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_app.databinding.ItemMovieBinding
import com.example.movie_app.domain.model.Movie
import com.squareup.picasso.Picasso

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemMovieBinding.bind(view)

    fun render(movie: Movie) {
        binding.name.text = movie.title
        if(movie.posterPath.isEmpty()) {
            return
        }
        binding.imageMovie.scaleType = (ImageView.ScaleType.FIT_XY)
        val imageURL = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
        Picasso.get().load(imageURL).into(binding.imageMovie)
    }
}