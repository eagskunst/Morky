package com.eagskunst.morky.data.datasource.local

import com.eagskunst.morky.data.dao.CharacterDao
import com.eagskunst.morky.data.model.local.toDto
import com.eagskunst.morky.domain.entity.CharacterEntity
import javax.inject.Inject

class CharacterLocalDataSource @Inject constructor(
    private val characterDao: CharacterDao,
) {
    suspend fun saveCharacters(characters: List<CharacterEntity>) {
        val dtos = characters.map { it.toDto() }.toTypedArray()
        characterDao.insertAll(*dtos)
    }

    suspend fun getCharacters() = characterDao.getAllCharacters()
}
