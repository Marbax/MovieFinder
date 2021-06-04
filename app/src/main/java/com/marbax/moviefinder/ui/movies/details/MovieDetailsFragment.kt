package com.marbax.moviefinder.ui.movies.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.marbax.moviefinder.R
import com.marbax.moviefinder.bll.network.ApiClient
import com.marbax.moviefinder.databinding.MovieDetailsFragmentBinding
import com.marbax.moviefinder.ui.generic.BaseViewBindingFragment
import com.marbax.moviefinder.ui.movies.details.adapter.CastAdapter
import com.marbax.moviefinder.util.observeEvent


class MovieDetailsFragment : BaseViewBindingFragment<MovieDetailsFragmentBinding>() {

    private val args: MovieDetailsFragmentArgs by navArgs()

    private val adapter = CastAdapter()

    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.movie_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        binding.movieDetailsCastsRv.adapter = adapter

        binding.movieDetailsAdditionalInfoHomepageContentTv.setOnClickListener {
            if (binding.movieDetailsAdditionalInfoHomepageContentTv.text.toString().isNotBlank()) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data =
                    Uri.parse(binding.movieDetailsAdditionalInfoHomepageContentTv.text.toString())
                startActivity(intent)
            }
        }

        viewModel.movie.observe(viewLifecycleOwner) {
            loadImage(binding.movieDetailsPosterIv, it.poster_path)
            binding.movieDetailsTitleTv.text = it.title
            binding.movieDetailsOverviewContentTv.text = it.overview
            binding.movieDetailsAdditionalInfoHomepageContentTv.text = it.homepage
            binding.movieDetailsAdditionalInfoTaglineContentTv.text = it.tagline
            binding.movieDetailsAdditionalInfoRuntimeContentTv.text = it.runtime
            adapter.submitList(it.credits.cast)
        }

        viewModel.posterLoadingEvent.observeEvent(viewLifecycleOwner) {
            handleUiEvent(it, binding.movieDetailsBackdropLoadingPb)
        }

        viewModel.getMovieDetails(args.movieId) { errorMsg ->
            Snackbar.make(view, errorMsg, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

    }

    private fun loadImage(iv: ImageView, imgUrl: String) {
        Glide.with(requireView())
            .load("${ApiClient.API_IMAGE_URL}/${imgUrl}")
            .placeholder(R.drawable.ic_image_search_24)
            .error(R.drawable.ic_broken_image_24)
            .centerCrop()
            .into(iv)
    }

    private fun initToolbar() {
        binding.movieDetailsCtl.title = ""
        binding.movieDetailsAbl.setExpanded(true)
        binding.movieDetailsAbl.addOnOffsetChangedListener(object :
            AppBarLayout.OnOffsetChangedListener {

            var isShown = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
                if (scrollRange == -1) {
                    appBarLayout?.totalScrollRange?.apply {
                        scrollRange = appBarLayout.totalScrollRange
                    }
                }

                if (scrollRange + verticalOffset == 0) {
                    binding.movieDetailsCtl.title = viewModel.movie.value?.title
                    isShown = true
                } else if (isShown)
                    binding.movieDetailsCtl.title = ""
                isShown = false
            }
        })

    }

    override fun initViewBinding(view: View): MovieDetailsFragmentBinding {
        return MovieDetailsFragmentBinding.bind(view)
    }
}