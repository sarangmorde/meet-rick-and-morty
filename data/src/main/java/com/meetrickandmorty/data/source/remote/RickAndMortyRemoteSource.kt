package com.meetrickandmorty.data.source.remote

import com.meetrickandmorty.domain.model.CharactersPagination

interface RickAndMortyRemoteSource {

    suspend fun getAllCharacters(offset: Int): Result<CharactersPagination>
}