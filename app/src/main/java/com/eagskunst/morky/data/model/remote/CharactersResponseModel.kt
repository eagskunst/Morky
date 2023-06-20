package com.eagskunst.morky.data.model.remote

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharactersResponseModel(
    @Json(name = "info")
    val requestInfo: ResponseMetadataModel,
    @Json(name = "results")
    val characters: List<CharacterModel>,
)
