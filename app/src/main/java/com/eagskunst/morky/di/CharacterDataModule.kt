package com.eagskunst.morky.di

import com.eagskunst.morky.data.repository.DefaultCharacterRepository
import com.eagskunst.morky.domain.repository.CharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class CharacterDataModule {
    @Provides
    fun provideRepository(repository: DefaultCharacterRepository): CharacterRepository = repository
}
