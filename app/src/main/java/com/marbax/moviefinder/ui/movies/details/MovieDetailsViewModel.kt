package com.marbax.moviefinder.ui.movies.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.marbax.moviefinder.bll.model.Movie
import com.marbax.moviefinder.bll.network.ApiClient
import com.marbax.moviefinder.ui.generic.BaseViewModel
import com.marbax.moviefinder.util.LoadingUIEvent
import com.marbax.moviefinder.util.EventMutableLiveData
import com.marbax.moviefinder.util.LoadingState
import com.marbax.moviefinder.util.call

class MovieDetailsViewModel : BaseViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    private val _posterLoadingEvent = EventMutableLiveData<LoadingUIEvent>()
    val posterLoadingEvent: EventMutableLiveData<LoadingUIEvent> = _posterLoadingEvent

    fun getMovieDetails(movieId: Int,onException: (error: String) -> Unit = {}) {
        _posterLoadingEvent.call(LoadingUIEvent.DisplayLoading(LoadingState.Loading))

        disposeOnCleared(
            ApiClient.getMovieInfo(movieId),
            { response ->
                response.apply {
                    _movie.value = this
                }
                _posterLoadingEvent.call(LoadingUIEvent.DisplayLoading(LoadingState.Success))

            }, { throwable ->
                Log.e(
                    "ApiError",
                    "Error\n$throwable"
                )
                onException(throwable.toString())
                _posterLoadingEvent.call(LoadingUIEvent.DisplayLoading(LoadingState.Error(throwable)))
            })
    }

}