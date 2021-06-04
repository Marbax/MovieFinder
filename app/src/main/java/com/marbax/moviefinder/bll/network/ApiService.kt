package com.marbax.moviefinder.bll.network

import com.marbax.moviefinder.bll.model.Movie
import com.marbax.moviefinder.bll.model.MovieResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getMovies(
        @Query("page") page: Int,
        @Query("language") lang: String,
        @Query("api_key") apiKey: String
    ): Single<MovieResponse>

    @GET("discover/movie")
    fun getMoviesByDateRange(
        @Query("primary_release_date.gte") dateFrom: String,
        @Query("primary_release_date.lte") dateTo: String,
        @Query("page") page: Int,
        @Query("vote_count.gte") votesGte: Int,
        @Query("sort_by") sortBy: String,
        @Query("api_key") apiKey: String
    ):
            Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieInfo(
        @Path("movie_id")
        movieId: Int,
        @Query("api_key")
        apiKey: String,
        @Query("append_to_response")
        credits: String
    ): Single<Movie>
}