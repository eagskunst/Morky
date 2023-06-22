package com.eagskunst.morky.util

import com.eagskunst.morky.data.model.local.toDto
import com.eagskunst.morky.data.model.remote.CharacterModel
import com.eagskunst.morky.data.model.remote.LocationModel
import com.eagskunst.morky.data.model.remote.OriginModel
import com.eagskunst.morky.data.model.remote.ResponseMetadataModel
import com.eagskunst.morky.data.model.remote.toEntity

object FakesFactory {
    fun createFakeCharacterModel() = CharacterModel(
        created = "",
        episode = listOf(),
        gender = "testle",
        id = 0,
        image = "test.jpg",
        name = "Test",
        status = "ALIVE",
        species = "testspecie",
        location = LocationModel("earth", ""),
        origin = OriginModel("earth", ""),
        type = "",
        url = "",
    )

    fun createFakeCharacterEntity() = createFakeCharacterModel().toEntity()

    fun createFakeCharacter() = createFakeCharacterEntity().toDto()

    fun createFakeResponseMetadataModel() = ResponseMetadataModel(
        count = 1,
        pages = 1,
        next = null,
        prev = null,
    )
}
