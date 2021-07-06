package com.meetrickandmorty.data.mapper

import com.meetrickandmorty.data.database.entity.CharacterEntity
import com.meetrickandmorty.domain.model.Character
import com.meetrickandmorty.domain.model.Location
import com.meetrickandmorty.domain.model.Origin

interface CharactersLocalMapper {
    fun transform(entities: List<CharacterEntity>): List<Character>

    fun transform(entity: CharacterEntity): Character

    fun transformToEntities(characters: List<Character>): List<CharacterEntity>

    fun transform(character: Character): CharacterEntity
}

class CharactersLocalMapperImpl : CharactersLocalMapper {

    override fun transform(entities: List<CharacterEntity>): List<Character> = entities.map {
        transform(it)
    }

    override fun transform(entity: CharacterEntity): Character = Character(
        created = entity.created,
        episode = entity.episode,
        gender = entity.gender,
        id = entity.id,
        image = entity.image,
        location = Location(
            entity.location.locationName,
            entity.location.locationUrl
        ),
        name = entity.name,
        origin = Origin(
            entity.origin.originName,
            entity.origin.originUrl
        ),
        species = entity.species,
        status = entity.status,
        type = entity.type,
        url = entity.url
    )

    override fun transformToEntities(characters: List<Character>) = characters.map {
        transform(it)
    }

    override fun transform(character: Character): CharacterEntity = CharacterEntity(
        created = character.created,
        episode = character.episode,
        gender = character.gender,
        id = character.id,
        image = character.image,
        location = com.meetrickandmorty.data.database.entity.Location(
            character.location.name,
            character.location.url
        ),
        name = character.name,
        origin = com.meetrickandmorty.data.database.entity.Origin(
            character.origin.name,
            character.origin.url
        ),
        species = character.species,
        status = character.status,
        type = character.type,
        url = character.url
    )
}