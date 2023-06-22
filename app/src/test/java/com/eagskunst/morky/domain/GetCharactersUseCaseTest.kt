package com.eagskunst.morky.domain

import com.eagskunst.morky.common.CoroutineDispatchers
import com.eagskunst.morky.domain.repository.CharacterRepository
import com.eagskunst.morky.util.FakesFactory
import com.eagskunst.morky.util.UtilDispatchers
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetCharactersUseCaseTest {
    lateinit var dispatchers: CoroutineDispatchers
    lateinit var testDispatcher: TestDispatcher
    lateinit var scheduler: TestCoroutineScheduler

    @MockK
    lateinit var repository: CharacterRepository

    lateinit var getCharactersUseCase: GetCharactersUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        UtilDispatchers.createTestCoroutineDispatchers().run {
            dispatchers = first
            testDispatcher = second
            scheduler = third
        }
        getCharactersUseCase = GetCharactersUseCase(dispatchers, repository)
    }

    @Test
    fun when_execute_then_useCaseReturnResultsFromRepository() {
        val characters = listOf(FakesFactory.createFakeCharacterEntity())
        coEvery { repository.getCharacters(any()) } returns flowOf(characters)

        runTest(
            // making sure the correct dispatcher is used so the test does not hangs
            context = dispatchers.io,
        ) {
            val actualCharacters = getCharactersUseCase.execute(2).single()
            assertThat(actualCharacters).isEqualTo(characters)
            coVerify { repository.getCharacters(eq(2)) }
        }
    }
}
