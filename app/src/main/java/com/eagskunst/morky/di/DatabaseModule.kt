package com.eagskunst.morky.di

import android.content.Context
import androidx.room.Room
import com.eagskunst.morky.common.database.DbConstants
import com.eagskunst.morky.common.database.MorkyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideDatabaseInstance(@ApplicationContext context: Context): MorkyDatabase {
        return Room.databaseBuilder(
            context,
            MorkyDatabase::class.java,
            DbConstants.NAME,
        ).build()
    }

    @Provides
    fun provideCharacterDao(db: MorkyDatabase) = db.characterDao()
}
