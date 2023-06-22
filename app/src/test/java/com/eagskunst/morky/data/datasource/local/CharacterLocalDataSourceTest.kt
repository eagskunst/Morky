package com.eagskunst.morky.data.datasource.local

import com.eagskunst.morky.data.dao.CharacterDao
import com.eagskunst.morky.data.model.local.Character
import com.eagskunst.morky.data.model.local.toDto
import com.eagskunst.morky.util.FakesFactory
import com.google.common.truth.Truth.assertThat
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CharacterLocalDataSourceTest {
    private val characterDao = mockk<CharacterDao>()
    private val dataSource = CharacterLocalDataSource(characterDao)

    @Test
    fun when_getCharacters_then_charactersFromDaoMustBeReturned() {
        val expectedCharacters = listOf(FakesFactory.createFakeCharacter())
        coEvery { characterDao.getAllCharacters() } returns expectedCharacters
        runTest {
            val actualCharacters = dataSource.getCharacters()
            assertThat(actualCharacters).isEqualTo(expectedCharacters)
            coVerify { characterDao.getAllCharacters() }
        }
    }

    @Test
    fun when_saveCharacters_then_daoMustSaveCharacters() {
        val toSaveCharacters = listOf(FakesFactory.createFakeCharacterEntity())
        val captured = mutableListOf<Character>()
        coEvery { characterDao.insertAll(*varargAll { captured.add(it) }) } just Runs
        runTest {
            dataSource.saveCharacters(toSaveCharacters)
            val expectedCharacters = toSaveCharacters.map { it.toDto() }.toTypedArray()
            assertThat(captured.toTypedArray()).isEqualTo(expectedCharacters)
        }
    }
}
