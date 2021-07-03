package com.meetrickandmorty.data.repository

import com.meetrickandmorty.data.source.remote.RickAndMortyRemoteSource
import com.meetrickandmorty.domain.model.CharactersPagination
import com.meetrickandmorty.domain.repository.RickAndMortyRepository

class RickAndMortyRepositoryImpl(
    private val remoteSource: RickAndMortyRemoteSource
) : RickAndMortyRepository {

    override suspend fun getAllCharacters(offset: Int, name: String): Result<CharactersPagination> {
        return remoteSource.getAllCharacters(offset, name)
    }
}