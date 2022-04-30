package com.example.movie_app.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_app.R
import com.example.movie_app.adapters.MovieAdapter
import com.example.movie_app.databinding.FragmentListBinding
import com.example.movie_app.domain.model.Movie
import com.example.movie_app.listeners.FragmentCallBackListener
import com.example.movie_app.listeners.OnItemClickListener
import com.example.movie_app.listeners.SearchCallBackListener
import com.example.movie_app.util.Constants.Companion.QUERY_PAGE_SIZE
import com.example.movie_app.ui.viewmodel.MovieViewModel

class ListFragment: Fragment() {

    private lateinit var binding: FragmentListBinding
    private val movieViewModel: MovieViewModel by viewModels()
    private var onCreateViewCalled = false
    private lateinit var adapter: MovieAdapter
    private val movies : MutableList<Movie> = mutableListOf()
    lateinit var fragmentCallBackListener: FragmentCallBackListener
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentListBinding.inflate(inflater, container, false)
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        if (!hasOnCreateViewBeenCalled()) {
            adapter = MovieAdapter()
            movieViewModel.onGetMovies()
            onCreateViewCalled = true
        }

        binding.recyclerView.adapter = adapter

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val visibleItemCount =
                        (binding.recyclerView.layoutManager as LinearLayoutManager).childCount
                    val totalItemCount =
                        (binding.recyclerView.layoutManager as LinearLayoutManager).itemCount
                    val firstVisibleItemPosition =
                        (binding.recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

                    val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
                    val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
                    val isNotAtBeginning = firstVisibleItemPosition >= 0
                    val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
                    val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                            isTotalMoreThanVisible && isScrolling
                    if(shouldPaginate) {
                        movieViewModel.onGetMovies()
                        isScrolling = false
                    } else {
                        binding.recyclerView.setPadding(0, 0, 0, 0)
                    }

                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }
        })

        movieViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressCircular.isVisible = it
        }

        movieViewModel.movies.observe(viewLifecycleOwner) {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                movies.addAll(it)
                adapter.addPeoples(it as ArrayList<Movie>)
                movieViewModel.currentPage++
                isLastPage = movieViewModel.currentPage == 501
                adapter.notifyDataSetChanged()
            }
        }

        movieViewModel.toastList.observe(viewLifecycleOwner) {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }

        adapter.setOnClickListener(object : OnItemClickListener {
            override fun onClick(view: View, position: Int) {
                fragmentCallBackListener.onCallBack(adapter.moviesFilterList[position].id)
            }
        })

        adapter.setSearchCallBack(object : SearchCallBackListener {
            override fun resultSearchFailed() {
                Toast.makeText(context, requireContext().resources.getString(R.string.seacrh_failed), Toast.LENGTH_SHORT).show()
            }
        })

        binding.movieSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })

        return binding.root
    }

    fun setFragmentCallBack1(fragmentCallBackListener: FragmentCallBackListener) {
        this.fragmentCallBackListener = fragmentCallBackListener
    }

    private fun hasOnCreateViewBeenCalled(): Boolean {
        return onCreateViewCalled
    }
}