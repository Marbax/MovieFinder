package com.marbax.moviefinder.bll.model

import com.google.gson.annotations.SerializedName

data class Credits (
    @SerializedName("cast") val cast:List<Cast>
)

