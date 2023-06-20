package com.eagskunst.morky.domain.repository

import com.eagskunst.morky.domain.entity.CharacterEntity
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun getCharacters(page: Int): Flow<List<CharacterEntity>>
}
