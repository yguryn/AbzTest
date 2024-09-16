package com.abz.data.model

import com.google.gson.annotations.SerializedName

data class LinksDTO(
    @SerializedName("next_url")
    val nextUrl: String?,
    @SerializedName("prev_url")
    val prevUrl: String?
)