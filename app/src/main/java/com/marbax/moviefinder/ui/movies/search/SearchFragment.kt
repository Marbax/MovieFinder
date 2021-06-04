package com.marbax.moviefinder.ui.movies.search

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.marbax.moviefinder.R
import com.marbax.moviefinder.SharedViewModel
import com.marbax.moviefinder.databinding.SearchFragmentBinding
import com.marbax.moviefinder.ui.generic.BaseViewBindingFragment
import com.marbax.moviefinder.util.Methods.Companion.formatDateToString
import java.util.*

class SearchFragment : BaseViewBindingFragment<SearchFragmentBinding>() {

    private val viewModel: SearchViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCalendarsDialogsListeners()
        initSearchBtnWithChecks()
    }

    private fun initCalendarsDialogsListeners() {
        val dateFrom: Calendar = sharedViewModel.searchDateFrom.value!!
        binding.searchDateFrom.text = formatDateToString(
            dateFrom[Calendar.YEAR],
            dateFrom[Calendar.MONTH],
            dateFrom[Calendar.DAY_OF_MONTH]
        )
        binding.searchDateFrom.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                    binding.searchDateFrom.text = formatDateToString(year, month, dayOfMonth)
                    viewModel.setDateFrom(year, month, dayOfMonth)
                },
                dateFrom[Calendar.YEAR],
                dateFrom[Calendar.MONTH],
                dateFrom[Calendar.DAY_OF_MONTH]
            ).show()
        }

        val dateTo: Calendar = sharedViewModel.searchDateTo.value!!
        binding.searchDateTo.text = formatDateToString(
            dateTo[Calendar.YEAR],
            dateTo[Calendar.MONTH],
            dateTo[Calendar.DAY_OF_MONTH]
        )
        binding.searchDateTo.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                    binding.searchDateTo.text = formatDateToString(year, month, dayOfMonth)
                    viewModel.setDateTo(year, month, dayOfMonth)
                },
                dateTo[Calendar.YEAR],
                dateTo[Calendar.MONTH],
                dateTo[Calendar.DAY_OF_MONTH]
            ).show()
        }
    }

    // FIXME: make checks simpler
    private fun initSearchBtnWithChecks() {
        binding.searchBtn.setOnClickListener {

            val calendarFrom = viewModel.searchDateFrom.value
            val calendarTo = viewModel.searchDateTo.value

            // if 'date from' chosen than compare it with another chosen date , otherwise compare with globally chosen date
            if (calendarFrom != null) {
                if (calendarTo != null && calendarFrom <= calendarTo) {
                    sharedViewModel.setDateFrom(viewModel.searchDateFrom.value!!)
                } else if (sharedViewModel.searchDateTo.value != null && calendarFrom <= sharedViewModel.searchDateTo.value!!) {
                    sharedViewModel.setDateFrom(viewModel.searchDateFrom.value!!)
                } else {
                    Snackbar.make(it, "First date interval isn't correct", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                    return@setOnClickListener
                }
            }
            // if 'date to' chosen than compare it with another chosen date , otherwise compare with globally chosen date
            if (calendarTo != null) {
                if (calendarFrom != null && calendarFrom <= calendarTo) {
                    sharedViewModel.setDateTo(viewModel.searchDateTo.value!!)
                } else if (sharedViewModel.searchDateFrom.value != null && calendarTo >= sharedViewModel.searchDateFrom.value!!) {
                    sharedViewModel.setDateTo(viewModel.searchDateTo.value!!)
                } else {
                    Snackbar.make(it, "Second date interval isn't correct", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                    return@setOnClickListener
                }
            }

            findNavController().navigateUp()
        }
    }

    override fun initViewBinding(view: View): SearchFragmentBinding {
        return SearchFragmentBinding.bind(view)
    }
}