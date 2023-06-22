package com.eagskunst.morky.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eagskunst.morky.domain.entity.CharacterEntity
import com.eagskunst.morky.domain.entity.CharacterStatus

@Entity(tableName = "characters")
data class Character(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "img_url") val imageUrl: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "species") val species: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "location_name") val location: String,
)

fun CharacterEntity.toDto() = Character(
    id = id,
    name = name,
    imageUrl = imageUrl,
    status = status.name,
    species = species,
    gender = gender,
    location = location,
)

fun Character.toEntity() = CharacterEntity(
    id = id,
    name = name,
    imageUrl = imageUrl,
    status = runCatching { CharacterStatus.valueOf(status) }.getOrDefault(CharacterStatus.UNKNOWN),
    species = species,
    gender = gender,
    location = location,
)
