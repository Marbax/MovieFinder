package com.marbax.moviefinder.ui.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marbax.moviefinder.bll.model.Movie
import com.marbax.moviefinder.bll.network.ApiClient
import com.marbax.moviefinder.ui.generic.BaseViewModel
import com.marbax.moviefinder.util.*
import com.marbax.moviefinder.util.Methods.Companion.formatDateToString
import java.util.*

class MovieListViewModel : BaseViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _showEvent = EventMutableLiveData<LoadingUIEvent>()
    val showEvent: EventLiveData<LoadingUIEvent> = _showEvent

    private var page = 1

    private val _lastQuery = MutableLiveData<String>()
    val lastQueryTest: LiveData<String> = _lastQuery

    private var lastQuery = ""

    fun getMovies() {
        _showEvent.call(LoadingUIEvent.DisplayLoading(LoadingState.Loading))

        resetOnMethodChanged(this::getMovies.name)

        disposeOnCleared(ApiClient.getMovies(page),
            { response ->
                response.results.apply {
                    _movies.value = _movies.value.orEmpty() + this
                }
                _showEvent.call(LoadingUIEvent.DisplayLoading(LoadingState.Success))
                ++page

            }, { throwable ->
                Log.e(
                    "ApiError",
                    "Error\n$throwable"
                )
                _showEvent.call(LoadingUIEvent.DisplayLoading(LoadingState.Error(throwable)))
            })
    }

    fun searchMovies(dateFrom: Calendar, dateTo: Calendar) {
        _showEvent.call(LoadingUIEvent.DisplayLoading(LoadingState.Loading))

        resetOnMethodChanged(this::searchMovies.name)

        disposeOnCleared(ApiClient.getMoviesByDateRange(
            formatDateToString(dateFrom),
            formatDateToString(dateTo),
            page
        ),
            { response ->
                response.results.apply {
                    _movies.value = _movies.value.orEmpty() + this
                }
                _showEvent.call(LoadingUIEvent.DisplayLoading(LoadingState.Success))
                ++page

            }, { throwable ->
                Log.e(
                    "ApiError",
                    "Error\n$throwable"
                )
                _showEvent.call(LoadingUIEvent.DisplayLoading(LoadingState.Error(throwable)))
            })
    }

    private fun resetOnMethodChanged(methodName: String) {
        if (lastQuery != methodName) {
            page = 1
            _lastQuery.value = methodName
            lastQuery = methodName
            _movies.value = listOf()
        }
    }
}