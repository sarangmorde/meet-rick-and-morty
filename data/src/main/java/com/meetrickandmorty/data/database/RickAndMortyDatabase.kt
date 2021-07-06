package com.meetrickandmorty.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.meetrickandmorty.data.database.dao.CharacterDao
import com.meetrickandmorty.data.database.entity.CharacterEntity

@Database(entities = [CharacterEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class RickAndMortyDatabase : RoomDatabase() {

    abstract fun characterDao(): CharacterDao
}