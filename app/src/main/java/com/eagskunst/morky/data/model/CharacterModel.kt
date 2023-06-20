package com.eagskunst.morky.data.model

import com.eagskunst.morky.domain.entity.CharacterEntity
import com.eagskunst.morky.domain.entity.CharacterStatus
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterModel(
    @Json(name = "created")
    val created: String,
    @Json(name = "episode")
    val episode: List<String>,
    @Json(name = "gender")
    val gender: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "image")
    val image: String,
    @Json(name = "location")
    val location: LocationModel,
    @Json(name = "name")
    val name: String,
    @Json(name = "origin")
    val origin: OriginModel,
    @Json(name = "species")
    val species: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "url")
    val url: String,
)

fun CharacterModel.toEntity() = CharacterEntity(
    id = id,
    name = name,
    imageUrl = image,
    status = CharacterStatus.valueOf(status) ?: CharacterStatus.UNKNOWN,
    species = species,
    gender = gender,
    location = location.name,
)
