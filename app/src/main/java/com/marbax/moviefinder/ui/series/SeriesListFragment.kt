package com.marbax.moviefinder.ui.series

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.marbax.moviefinder.R
import com.marbax.moviefinder.databinding.SeriesListFragmentBinding
import com.marbax.moviefinder.ui.generic.BaseViewBindingFragment

class SeriesListFragment : BaseViewBindingFragment<SeriesListFragmentBinding>() {

    private val viewModel: SeriesListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.series_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textSlideshow.text = it
        })
    }

    override fun initViewBinding(view: View): SeriesListFragmentBinding {
        return SeriesListFragmentBinding.bind(view)
    }
}