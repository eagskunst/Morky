package com.eagskunst.morky.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.eagskunst.morky.domain.entity.CharacterEntity

@Composable
fun Character(character: CharacterEntity) {
    Column(verticalArrangement = Arrangement.SpaceBetween) {
        Text(text = character.name)
        Text(text = character.gender)
        Text(text = character.species)
        Text(text = character.status.name)
    }
}

@Composable
fun CharacterLazyList(characters: List<CharacterEntity>) {
    LazyColumn {
        items(
            characters.size,
            key = { i -> characters[i].id },
            contentType = { i -> characters[i] },
        ) { i ->
            Character(character = characters[i])
        }
    }
}
