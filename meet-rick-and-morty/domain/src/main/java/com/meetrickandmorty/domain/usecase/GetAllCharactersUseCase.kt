package com.meetrickandmorty.domain.usecase

import com.meetrickandmorty.domain.model.CharactersPagination

interface GetAllCharactersUseCase {

    suspend fun getAllCharacters(offSet: Int): Result<CharactersPagination>
}