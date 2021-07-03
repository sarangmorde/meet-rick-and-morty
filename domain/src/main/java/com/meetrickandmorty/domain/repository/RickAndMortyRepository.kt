package com.meetrickandmorty.domain.repository

import com.meetrickandmorty.domain.model.CharactersPagination
import com.meetrickandmorty.domain.utils.Constants.EMPTY_STRING

interface RickAndMortyRepository {

    suspend fun getAllCharacters(offset: Int, name: String = EMPTY_STRING): Result<CharactersPagination>
}