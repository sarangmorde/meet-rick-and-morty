package com.meetrickandmorty.domain.usecase


import com.meetrickandmorty.domain.model.CharactersPagination
import com.meetrickandmorty.domain.repository.RickAndMortyRepository

class GetAllCharactersUseCaseImpl(
    val repository: RickAndMortyRepository
) : GetAllCharactersUseCase {

    override suspend fun getAllCharacters(offSet: Int, name: String): Result<CharactersPagination> {
        return repository.getAllCharacters(offSet, name)
    }
}