package com.marbax.moviefinder.bll.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(

	@SerializedName("page") val page: Int,
	@SerializedName("results") val results: List<Movie>,
	@SerializedName("total_pages") val total_pages: Int,
	@SerializedName("total_results") val total_results: Int
)