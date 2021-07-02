package com.meetrickandmorty.data.mapper

import com.meetrickandmorty.data.source.remote.response.CharacterListResponse
import com.meetrickandmorty.domain.model.CharactersPagination

interface CharactersRemoteMapper {
    fun transform(response: CharacterListResponse): CharactersPagination
}