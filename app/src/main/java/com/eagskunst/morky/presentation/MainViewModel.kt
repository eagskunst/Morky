package com.eagskunst.morky.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eagskunst.morky.domain.GetCharactersUseCase
import com.eagskunst.morky.domain.entity.CharacterEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
) : ViewModel() {
    private val mutableState = MutableStateFlow<MainViewState>(MainViewState.Loading)
    val state: StateFlow<MainViewState>
        get() = mutableState
    var savedCharacters = listOf<CharacterEntity>()
        @VisibleForTesting get

        @VisibleForTesting set
    private val inputFlow = MutableStateFlow("")
    private var page = 1

    fun getCharacters() {
        viewModelScope.launch {
            getCharactersUseCase.execute(page)
                .catch { cause ->
                    Timber.e(cause, "Error getting characters from page $page")
                    mutableState.value = MainViewState.Error(savedCharacters, cause)
                }.collect { characters ->
                    savedCharacters = savedCharacters + characters
                    mutableState.value = MainViewState.Characters(savedCharacters)
                }
        }
    }

    fun onNewFilterInput(input: String) {
        if (savedCharacters.isEmpty()) {
            return
        }
        inputFlow.value = input
        if (inputFlow.subscriptionCount.value >= 1) {
            // just collect once
            return
        }
        collectInputFlow()
    }

    private fun collectInputFlow() {
        viewModelScope.launch {
            inputFlow
                .debounce(300)
                .collect { input ->
                    val characters = if (input.isEmpty()) {
                        savedCharacters
                    } else {
                        savedCharacters.filter { it.name.contains(input, ignoreCase = true) }
                    }
                    mutableState.value = MainViewState.Characters(characters)
                }
        }
    }
}
