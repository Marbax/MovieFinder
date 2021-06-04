package com.marbax.moviefinder.bll.model

import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("cast_id") val castId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("profile_path") val profilePath: String,
    @SerializedName("id") val id: Int,
    @SerializedName("popularity") val popularity: String,
    @SerializedName("character") val character: String
)