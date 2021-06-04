package com.marbax.moviefinder.util

sealed class LoadingUIEvent {
    class DisplayLoading(val loadingState: LoadingState) : LoadingUIEvent()
}

