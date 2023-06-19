package com.eagskunst.morky.di

import com.eagskunst.morky.common.CoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ThreadModule {
    @Provides
    fun provideDefaultDispatchers() = CoroutineDispatchers()
}
