package com.eagskunst.morky.data.repository

import com.eagskunst.morky.data.datasource.CharactersRemoteDataSource
import com.eagskunst.morky.domain.entity.CharacterEntity
import com.eagskunst.morky.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultCharacterRepository @Inject constructor(
    private val remoteDataSource: CharactersRemoteDataSource,
) : CharacterRepository {

    override suspend fun getCharacters(page: Int): Flow<List<CharacterEntity>> {
        return remoteDataSource.getCharacters(page)
    }
}
