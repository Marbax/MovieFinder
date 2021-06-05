package com.marbax.moviefinder.bll.network

import com.marbax.moviefinder.bll.model.Movie
import com.marbax.moviefinder.bll.model.MovieResponse
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "db1ff3baf003eb28c227e02bda6b00b2"
    private const val DEF_LANG = "en-US"
    private const val FIRST_PAGE = 1
    private const val SAFE_SEARCH_VOTES_COUNT = 50
    const val API_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    private const val API_SORT_BY = "primary_release_date.desc"
    private const val CREDITS = "credits"

    private val apiService = createApiService()

    fun getMovies(
        page: Int = FIRST_PAGE, // min 1 , max 1000
        lang: String = DEF_LANG, // pattern: ([a-z]{2})-([A-Z]{2})
        sortBy: String = API_SORT_BY,
        apiKey: String = API_KEY
    ): Single<MovieResponse> {
        return apiService.getMovies(page, lang, sortBy, apiKey)
    }

    fun getMoviesByDateRange(
        dateFrom: String,
        dateTo: String,
        page: Int = FIRST_PAGE,
        votesGte: Int = SAFE_SEARCH_VOTES_COUNT, // votes count greater or equivalent (trying to exclude trash)
        sortBy: String = API_SORT_BY,
        apiKey: String = API_KEY
    ): Single<MovieResponse> {
        return apiService.getMoviesByDateRange(dateFrom, dateTo, page, votesGte, sortBy, apiKey)
    }

    fun getMovieInfo(
        movieId: Int,
        apiKey: String = API_KEY,
        credits: String = CREDITS
    ): Single<Movie> {
        return apiService.getMovieInfo(movieId, apiKey, credits)
    }

    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(createClient())
            .build()
    }

    private fun createClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    private fun createApiService(): ApiService {
        val retrofit = createRetrofit()
        return retrofit.create(ApiService::class.java)
    }
}