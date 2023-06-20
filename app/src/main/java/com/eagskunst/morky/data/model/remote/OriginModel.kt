package com.eagskunst.morky.data.model.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OriginModel(
    @Json(name = "name")
    val name: String,
    @Json(name = "url")
    val url: String,
)
