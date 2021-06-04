package com.marbax.moviefinder.ui.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.marbax.moviefinder.R
import com.marbax.moviefinder.SharedViewModel
import com.marbax.moviefinder.databinding.MovieListFragmentBinding
import com.marbax.moviefinder.ui.generic.BaseViewBindingFragment
import com.marbax.moviefinder.ui.movies.adapter.MoviesAdapter
import com.marbax.moviefinder.util.observeEvent

class MovieListFragment : BaseViewBindingFragment<MovieListFragmentBinding>() {

    private val viewModel: MovieListViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val adapter = MoviesAdapter {
        onViewHolderClick(it)
    }

    private fun onViewHolderClick(movieId: Int) {
        val navDir = MovieListFragmentDirections.actionNavMoviesToMovieDetailsFragment(movieId)
        findNavController().navigate(navDir)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.movie_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.moviesRv.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.moviesRv.adapter = adapter

        // updating movies
        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        // initiate observers to make search query
        //initSearchObservers()

        //TODO: it should return list of found movies
        //FIXME: it mb an bad idea to to search on observing

        // go to search fragment to make search query
        binding.moviesSearchFab.setOnClickListener {
            val navDir = MovieListFragmentDirections.actionNavMoviesToSearchFragment()
            findNavController().navigate(navDir)

            //Snackbar.make(it, "Replace with your own app bar action", Snackbar.LENGTH_LONG)
            //    .setAction("Action", null).show()
        }

        // display loading on query to the api
        viewModel.showEvent.observeEvent(viewLifecycleOwner) {
            handleUiEvent(it, binding.moviesListPb)
        }

        // track recycler view scroll position to load new page
        binding.moviesRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    viewModel.getMovies()
                }
            }
        })

        //get first page of movies
        viewModel.getMovies()
    }

    //FIXME: look's pretty weird , change search logic
    private fun initSearchObservers() {
        sharedViewModel.searchDateFrom.observe(viewLifecycleOwner) {
            viewModel.searchMovies(
                sharedViewModel.searchDateFrom.value!!,
                sharedViewModel.searchDateTo.value!!
            )
        }
        sharedViewModel.searchDateTo.observe(viewLifecycleOwner) {
            viewModel.searchMovies(
                sharedViewModel.searchDateFrom.value!!,
                sharedViewModel.searchDateTo.value!!
            )
        }
    }

    override fun initViewBinding(view: View): MovieListFragmentBinding {
        return MovieListFragmentBinding.bind(view)
    }
}