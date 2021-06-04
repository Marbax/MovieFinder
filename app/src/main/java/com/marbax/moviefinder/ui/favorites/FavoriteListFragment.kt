package com.marbax.moviefinder.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.marbax.moviefinder.R
import com.marbax.moviefinder.databinding.FavoritesListFragmentBinding
import com.marbax.moviefinder.ui.generic.BaseViewBindingFragment

class FavoriteListFragment : BaseViewBindingFragment<FavoritesListFragmentBinding>() {

    private val viewModel: FavoriteListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorites_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textGallery.text = it
        })
    }

    override fun initViewBinding(view: View): FavoritesListFragmentBinding {
        return FavoritesListFragmentBinding.bind(view)
    }
}