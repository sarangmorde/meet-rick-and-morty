package com.meetrickandmorty.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.meetrickandmorty.domain.utils.Constants.EMPTY_STRING

@Entity
data class CharacterEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String = EMPTY_STRING,
    @ColumnInfo(name = "created") val created: String,
    @ColumnInfo(name = "episode") val episode: List<String>,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "species") val species: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "url") val url: String,
    @Embedded val location: Location,
    @Embedded val origin: Origin
)

@Entity
data class Location(
    @ColumnInfo(name = "locationName") val locationName: String,
    @ColumnInfo(name = "locationUrl") val locationUrl: String
)

@Entity
data class Origin(
    @ColumnInfo(name = "originName") val originName: String,
    @ColumnInfo(name = "originUrl") val originUrl: String
)