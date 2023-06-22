package com.eagskunst.morky.data.repository

import com.eagskunst.morky.data.datasource.local.CharacterLocalDataSource
import com.eagskunst.morky.data.datasource.remote.CharactersRemoteDataSource
import com.eagskunst.morky.data.model.local.toEntity
import com.eagskunst.morky.util.FakesFactory
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DefaultCharacterRepositoryTest {
    @MockK
    lateinit var remoteDataSource: CharactersRemoteDataSource

    @MockK
    lateinit var localDataSource: CharacterLocalDataSource

    @InjectMockKs
    lateinit var repository: DefaultCharacterRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun given_dataInDb_when_getCharacters_then_flowHasResultFromLocalDS() {
        val characters = listOf(FakesFactory.createFakeCharacter())
        coEvery { localDataSource.getCharacters() } returns characters

        runTest {
            val expectedCharacters = characters.map { it.toEntity() }
            val actualCharacters = repository.getCharacters(1).single()
            assertThat(actualCharacters).isEqualTo(expectedCharacters)
            coVerify(exactly = 0) {
                remoteDataSource.getCharacters(any())
                localDataSource.saveCharacters(any())
            }
        }
    }

    @Test
    fun given_dataNotInDb_when_getCharacters_then_flowHasResultFromRemoteDSAndCachesIt() {
        val characters = listOf(
            FakesFactory.createFakeCharacterEntity(),
            FakesFactory.createFakeCharacterEntity().copy(id = 2),
        )
        coEvery { localDataSource.getCharacters() } returns listOf()
        coEvery { remoteDataSource.getCharacters(any()) } returns flowOf(characters)
        coEvery { localDataSource.saveCharacters(any()) } just Runs

        runTest {
            val actualCharacters = repository.getCharacters(1).single()
            assertThat(actualCharacters).isEqualTo(characters)
            coVerify(exactly = 1) {
                localDataSource.getCharacters()
                remoteDataSource.getCharacters(eq(1))
                localDataSource.saveCharacters(eq(characters))
            }
        }
    }
}
