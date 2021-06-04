package com.marbax.moviefinder.ui.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import com.marbax.moviefinder.R
import com.marbax.moviefinder.bll.model.Movie
import com.marbax.moviefinder.bll.network.ApiClient


class MoviesAdapter(private val onViewHolderClick: (id: Int) -> Unit) :
    ListAdapter<Movie, MoviesAdapter.MovieViewHolder>(DishDiffUtil()) {

    class MovieViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {


        fun bind(item: Movie) {
            itemView.findViewById<MaterialTextView>(R.id.movie_title_tv).text =
                item.title
            itemView.findViewById<MaterialTextView>(R.id.movie_release_date_tv).text =
                item.release_date
            itemView.findViewById<MaterialTextView>(R.id.movie_rating_tv).text =
                item.vote_average.toString()

            loadImage(itemView.findViewById<ImageView>(R.id.movie_poster_iv), item.poster_path)
        }

        private fun loadImage(iv: ImageView, imgUrl: String) {
            Glide.with(itemView)
                .load("${ApiClient.API_IMAGE_URL}/${imgUrl}")
                .placeholder(R.drawable.ic_image_search_24)
                .error(R.drawable.ic_broken_image_24)
                .centerCrop()
                .into(iv)
        }
    }

    class DishDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_card, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
            holder.itemView.setOnClickListener { onViewHolderClick(item.id) }
        }
    }
}