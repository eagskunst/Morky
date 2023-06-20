package com.eagskunst.morky.di

import com.eagskunst.morky.BuildConfig
import com.eagskunst.morky.data.api.CharacterApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ServicesModule {
    @Provides
    fun provideMoshiInstance() = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    fun provideRetrofit(moshi: Moshi, client: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BuildConfig.API_URL)
        .client(client)

    @Provides
    fun provideCharacterApi(retrofit: Retrofit) = retrofit.create(CharacterApi::class.java)
}
