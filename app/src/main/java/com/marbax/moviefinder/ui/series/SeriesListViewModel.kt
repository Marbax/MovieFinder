package com.marbax.moviefinder.ui.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SeriesListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is series Fragment"
    }
    val text: LiveData<String> = _text
}