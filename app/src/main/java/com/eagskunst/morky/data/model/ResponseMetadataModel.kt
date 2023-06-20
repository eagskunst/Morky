package com.eagskunst.morky.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseMetadataModel(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?,
)
