package com.marbax.moviefinder.ui.generic

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.marbax.moviefinder.util.LoadingState
import com.marbax.moviefinder.util.LoadingUIEvent

abstract class BaseViewBindingFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding
        get() = _binding!!

    abstract fun initViewBinding(view: View): VB

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = initViewBinding(view)
        super.onViewCreated(view, savedInstanceState)
    }

    fun handleUiEvent(event: LoadingUIEvent, progressBar: ProgressBar) {
        when (event) {
            is LoadingUIEvent.DisplayLoading -> displayLoadingState(event.loadingState, progressBar)
        }
    }

    private fun displayLoadingState(loadingState: LoadingState, progressBar: ProgressBar) {
        when (loadingState) {
            is LoadingState.Error -> {
                progressBar.isVisible = false
                Toast.makeText(context, loadingState.throwable.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
            LoadingState.Loading -> progressBar.isVisible = true
            LoadingState.Success -> progressBar.isVisible = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
