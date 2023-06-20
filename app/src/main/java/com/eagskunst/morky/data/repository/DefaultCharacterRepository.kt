package com.eagskunst.morky.data.repository

import com.eagskunst.morky.data.datasource.local.CharacterLocalDataSource
import com.eagskunst.morky.data.datasource.remote.CharactersRemoteDataSource
import com.eagskunst.morky.data.model.local.toEntity
import com.eagskunst.morky.domain.entity.CharacterEntity
import com.eagskunst.morky.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DefaultCharacterRepository @Inject constructor(
    private val remoteDataSource: CharactersRemoteDataSource,
    private val localDataSource: CharacterLocalDataSource,
) : CharacterRepository {

    override suspend fun getCharacters(page: Int): Flow<List<CharacterEntity>> {
        val dtos = localDataSource.getCharacters()
        if (dtos.isNotEmpty()) {
            return flow { emit(dtos.map { it.toEntity() }) }
        }
        return remoteDataSource
            .getCharacters(page)
            .onEach { characters ->
                localDataSource.saveCharacters(characters)
            }
    }
}
