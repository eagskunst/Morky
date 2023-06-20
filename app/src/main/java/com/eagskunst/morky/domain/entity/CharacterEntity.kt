package com.eagskunst.morky.domain.entity

data class CharacterEntity(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val status: CharacterStatus,
    val species: String,
    val gender: String,
    val location: String,
)
