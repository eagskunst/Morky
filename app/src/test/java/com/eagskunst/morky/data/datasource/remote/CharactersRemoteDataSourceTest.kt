package com.eagskunst.morky.data.datasource.remote

import com.eagskunst.morky.data.api.CharacterApi
import com.eagskunst.morky.data.model.remote.CharactersResponseModel
import com.eagskunst.morky.data.model.remote.toEntity
import com.eagskunst.morky.util.FakesFactory
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CharactersRemoteDataSourceTest {
    private val api = mockk<CharacterApi>()
    private val dataSource = CharactersRemoteDataSource(api)

    @Test
    fun when_getCharacters_then_charactersMustBeFromApi() {
        val characters = listOf(FakesFactory.createFakeCharacterModel())
        val model = CharactersResponseModel(
            FakesFactory.createFakeResponseMetadataModel(),
            characters,
        )
        coEvery { api.getCharacters(any()) } returns model
        runTest {
            val expectedCharacters = characters.map { it.toEntity() }
            val actualCharacters = dataSource.getCharacters(1).single()
            assertThat(actualCharacters).isEqualTo(expectedCharacters)
        }
    }
}
