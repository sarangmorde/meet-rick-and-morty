package com.meetrickandmorty.data.repository

import com.meetrickandmorty.data.source.local.RickAndMortyLocalSource
import com.meetrickandmorty.data.source.remote.RickAndMortyRemoteSource
import com.meetrickandmorty.domain.model.CharactersPagination
import com.meetrickandmorty.domain.repository.RickAndMortyRepository

class RickAndMortyRepositoryImpl(
    private val localSource: RickAndMortyLocalSource,
    private val remoteSource: RickAndMortyRemoteSource
) : RickAndMortyRepository {

    override suspend fun getAllCharacters(offset: Int, name: String): Result<CharactersPagination> {
        val next = try {
            localSource.getPaginationInfo().next.run {
                substring(lastIndexOf("=") + 1)
            }.toInt()
        } catch (e: NumberFormatException) {
            1
        }

        val charactersPagination = localSource.getAllCharacters()
        val characters = charactersPagination.getOrNull()?.characters ?: emptyList()

        return if ((next > offset || (characters.isNotEmpty() && offset == 1)) && name.isEmpty()) {
            charactersPagination
        } else {
            val result = remoteSource.getAllCharacters(offset, name)
            if (result.isSuccess) {
                result.getOrNull()?.let {
                    localSource.insertAll(it.characters)
                    localSource.savePaginationInfo(it.info)
                }
            }
            result
        }
    }
}