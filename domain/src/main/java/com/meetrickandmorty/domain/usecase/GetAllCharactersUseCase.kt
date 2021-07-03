package com.meetrickandmorty.domain.usecase

import com.meetrickandmorty.domain.model.CharactersPagination
import com.meetrickandmorty.domain.utils.Constants.EMPTY_STRING

interface GetAllCharactersUseCase {

    suspend fun getAllCharacters(
        offSet: Int,
        name: String = EMPTY_STRING
    ): Result<CharactersPagination>
}