package com.meetrickandmorty.data.source.remote

import com.meetrickandmorty.domain.model.CharactersPagination
import com.meetrickandmorty.domain.utils.Constants.EMPTY_STRING

interface RickAndMortyRemoteSource {

    suspend fun getAllCharacters(
        offset: Int,
        name: String = EMPTY_STRING
    ): Result<CharactersPagination>
}