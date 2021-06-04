package com.marbax.moviefinder.ui.movies.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class SearchViewModel : ViewModel() {
    private val _searchDateFrom = MutableLiveData<Calendar>()
    val searchDateFrom: LiveData<Calendar> = _searchDateFrom

    private val _searchDateTo = MutableLiveData<Calendar>()
    val searchDateTo: LiveData<Calendar> = _searchDateTo

    fun setDateFrom(calendar: Calendar) {
        _searchDateFrom.value = calendar
    }

    fun setDateFrom(year: Int, month: Int, dayOfWeek: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfWeek)
        _searchDateFrom.value = calendar
    }

    fun setDateTo(calendar: Calendar) {
        _searchDateTo.value = calendar
    }

    fun setDateTo(year: Int, month: Int, dayOfWeek: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfWeek)
        _searchDateTo.value = calendar
    }

}