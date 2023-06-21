package com.eagskunst.morky.presentation

import com.eagskunst.morky.domain.entity.CharacterEntity

sealed class MainViewState {
    object Loading : MainViewState()
    data class Error(val previousCharacters: List<CharacterEntity>, val cause: Throwable) :
        MainViewState()

    data class Characters(val characters: List<CharacterEntity>) : MainViewState()
}
