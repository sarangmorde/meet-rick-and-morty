package com.meetrickandmorty.domain.repository

import com.meetrickandmorty.domain.model.CharactersPagination

interface RickAndMortyRepository {

    suspend fun getAllCharacters(offset: Int): Result<CharactersPagination>
}