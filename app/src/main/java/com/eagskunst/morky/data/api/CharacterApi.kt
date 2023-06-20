package com.eagskunst.morky.data.api

import com.eagskunst.morky.data.model.remote.CharactersResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {
    @GET("/character")
    suspend fun getCharacters(@Query(value = "page") page: Int): CharactersResponseModel
}
