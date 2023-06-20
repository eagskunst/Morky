package com.eagskunst.morky.data.datasource

import com.eagskunst.morky.data.api.CharacterApi
import com.eagskunst.morky.data.model.toEntity
import com.eagskunst.morky.domain.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CharactersRemoteDataSource @Inject constructor(
    private val api: CharacterApi,
) {
    suspend fun getCharacters(page: Int): Flow<List<CharacterEntity>> {
        return flow {
            val entities = api.getCharacters(page).characters.map { it.toEntity() }
            emit(entities)
        }
    }
}
