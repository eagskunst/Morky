package com.eagskunst.morky.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eagskunst.morky.data.dao.CharacterDao
import com.eagskunst.morky.data.model.local.Character

@Database(
    entities = [
        Character::class,
    ],
    version = DbConstants.VERSION,
    exportSchema = true,
)
abstract class MorkyDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
