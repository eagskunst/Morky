package com.eagskunst.morky.presentation

import com.eagskunst.morky.domain.GetCharactersUseCase
import com.eagskunst.morky.util.FakesFactory
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {
    @MockK
    lateinit var getCharactersUseCase: GetCharactersUseCase

    @InjectMockKs
    lateinit var viewModel: MainViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }

    @Test
    fun when_init_verify_initialState() {
        assertThat(viewModel.state.value).isEqualTo(MainViewState.Loading)
    }

    @Test
    fun given_exception_when_getCharacters_then_stateMustBeError() {
        val exception = IllegalArgumentException()
        coEvery { getCharactersUseCase.execute(any()) } returns flow {
            throw exception
        }
        viewModel.getCharacters()
        testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.state.value).isEqualTo(MainViewState.Error(listOf(), exception))
    }

    @Test
    fun given_characters_when_getCharacters_then_stateMustBeError() {
        val characters = listOf(
            FakesFactory.createFakeCharacterEntity(),
            FakesFactory.createFakeCharacterEntity().copy(id = 2),
        )
        coEvery { getCharactersUseCase.execute(any()) } returns flowOf(characters)
        viewModel.getCharacters()
        testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.state.value).isEqualTo(MainViewState.Characters(characters))
        assertThat(viewModel.savedCharacters).isEqualTo(characters)
    }

    @Test
    fun given_noCharactersSaved_when_onNewFilterInput_then_nothingStateRemainsTheSame() {
        val expectedState = MainViewState.Loading
        viewModel.onNewFilterInput("input")
        assertThat(viewModel.state.value).isEqualTo(expectedState)
    }

    @Test
    fun given_rickInputAndCharactersSaved_when_onNewFilterInput_then_stateUpdatesWithFilterCharacter() {
        val characters = listOf(
            FakesFactory.createFakeCharacterEntity(),
            FakesFactory.createFakeCharacterEntity().copy(id = 2),
            FakesFactory.createFakeCharacterEntity().copy(name = "Rick"),
        )
        val input = "rick"

        viewModel.savedCharacters = characters
        viewModel.onNewFilterInput(input)

        testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.state.value is MainViewState.Characters).isTrue()
        val filterCharacters = (viewModel.state.value as MainViewState.Characters).characters
        assertThat(filterCharacters).isEqualTo(
            characters.filter {
                it.name.contains(
                    input,
                    ignoreCase = true,
                )
            },
        )
    }

    @Test
    fun given_emptyInputAndCharactersSaved_when_onNewFilterInput_then_stateUpdatesWithSavedCharacters() {
        val characters = listOf(
            FakesFactory.createFakeCharacterEntity(),
            FakesFactory.createFakeCharacterEntity().copy(id = 2),
            FakesFactory.createFakeCharacterEntity().copy(name = "Rick"),
        )
        val input = ""

        viewModel.savedCharacters = characters
        viewModel.onNewFilterInput(input)

        testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.state.value is MainViewState.Characters).isTrue()
        val filterCharacters = (viewModel.state.value as MainViewState.Characters).characters
        assertThat(filterCharacters).isEqualTo(characters)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
