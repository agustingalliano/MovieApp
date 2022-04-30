package com.example.movie_app.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_app.R
import com.example.movie_app.domain.model.Movie
import com.example.movie_app.listeners.OnItemClickListener
import com.example.movie_app.listeners.SearchCallBackListener
import com.example.movie_app.ui.view.viewholders.MovieViewHolder
import java.util.*
import kotlin.collections.ArrayList

class MovieAdapter : RecyclerView.Adapter<MovieViewHolder>(), Filterable {

    private var movies :ArrayList<Movie> = ArrayList()
    private lateinit var onItemClickListener: OnItemClickListener
    private lateinit var searchCallBackListener: SearchCallBackListener
    var moviesFilterList: ArrayList<Movie> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.render(moviesFilterList[position])
        holder.itemView.setOnClickListener { v ->
            if (v != null) onItemClickListener.onClick(v, holder.bindingAdapterPosition)
        }
    }

    override fun getItemCount(): Int {
        return moviesFilterList.size
    }

    fun addPeoples(arrayList: ArrayList<Movie>) {
        this.movies.addAll(arrayList)
        moviesFilterList = this.movies
    }

    fun setOnClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun setSearchCallBack(searchCallBackListener: SearchCallBackListener) {
        this.searchCallBackListener = searchCallBackListener
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {val charSearch = constraint.toString()
                moviesFilterList = if (charSearch.isEmpty()) {
                    movies
                } else { val resultList = ArrayList<Movie>()
                    for (row in movies) {
                        if (row.title.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = moviesFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                moviesFilterList = results?.values as ArrayList<Movie>
                if(moviesFilterList.isEmpty()) searchCallBackListener.resultSearchFailed()
                notifyDataSetChanged()
            }
        }
    }
}