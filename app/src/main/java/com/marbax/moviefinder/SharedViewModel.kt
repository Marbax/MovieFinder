package com.marbax.moviefinder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class SharedViewModel : ViewModel() {

    private val _searchDateFrom = MutableLiveData<Calendar>()
    val searchDateFrom: LiveData<Calendar> = _searchDateFrom

    private val _searchDateTo = MutableLiveData<Calendar>()
    val searchDateTo: LiveData<Calendar> = _searchDateTo

    init {
        _searchDateTo.value = Calendar.getInstance()

        val weekAgo = Calendar.getInstance()
        weekAgo.add(Calendar.DAY_OF_YEAR, -7)
        _searchDateFrom.value = weekAgo
    }


    fun setDateFrom(calendar: Calendar) {
        _searchDateFrom.value = calendar
    }

    fun setDateTo(calendar: Calendar) {
        _searchDateTo.value = calendar
    }
}