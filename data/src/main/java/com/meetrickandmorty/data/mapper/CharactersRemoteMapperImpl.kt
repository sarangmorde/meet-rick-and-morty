package com.meetrickandmorty.data.mapper

import com.meetrickandmorty.data.source.remote.response.CharacterListResponse
import com.meetrickandmorty.data.source.remote.response.Info
import com.meetrickandmorty.domain.model.Character
import com.meetrickandmorty.domain.model.CharactersPagination
import com.meetrickandmorty.domain.model.InfoModel
import com.meetrickandmorty.domain.model.Location
import com.meetrickandmorty.domain.model.Origin

class CharactersRemoteMapperImpl : CharactersRemoteMapper {

    override fun transform(response: CharacterListResponse): CharactersPagination {
        return CharactersPagination(
            info = transformInfo(response.info),
            characters = response.results?.map {
                Character(
                    created = it.created.orEmpty(),
                    episode = it.episode ?: emptyList(),
                    gender = it.gender.orEmpty(),
                    id = it.id ?: 0,
                    image = it.image.orEmpty(),
                    location = Location(
                        it.location?.name.orEmpty(),
                        it.location?.url.orEmpty()
                    ),
                    name = it.name.orEmpty(),
                    origin = Origin(
                        it.origin?.name.orEmpty(),
                        it.origin?.url.orEmpty()
                    ),
                    species = it.species.orEmpty(),
                    status = it.status.orEmpty(),
                    type = it.type.orEmpty(),
                    url = it.url.orEmpty()
                )
            } ?: emptyList()
        )
    }

    private fun transformInfo(info: Info?) = InfoModel(
        count = info?.count ?: 0,
        next = info?.next.orEmpty(),
        pages = info?.pages ?: 0,
        prev = info?.prev.orEmpty()
    )
}