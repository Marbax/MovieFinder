package com.marbax.moviefinder.ui.movies.details.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marbax.moviefinder.R
import com.marbax.moviefinder.bll.model.Cast
import com.marbax.moviefinder.bll.network.ApiClient

class CastAdapter : ListAdapter<Cast, CastAdapter.CastViewHolder>(DishDiffUtil()) {

    class CastViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {


        fun bind(item: Cast) {
            itemView.findViewById<TextView>(R.id.cast_name_tv).text = item.name
            itemView.findViewById<TextView>(R.id.character_name_tv).text = item.character

            loadImage(itemView.findViewById<ImageView>(R.id.cast_poster_iv), item.profilePath)
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

    class DishDiffUtil : DiffUtil.ItemCallback<Cast>() {
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.castId == newItem.castId
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cast_card, parent, false)
        return CastViewHolder(view)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
        }
    }

}