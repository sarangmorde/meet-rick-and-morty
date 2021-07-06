package com.meetrickandmorty.data.source.local

import com.meetrickandmorty.data.database.dao.CharacterDao
import com.meetrickandmorty.data.mapper.CharactersLocalMapper
import com.meetrickandmorty.domain.model.Character
import com.meetrickandmorty.domain.model.CharactersPagination
import com.meetrickandmorty.domain.model.InfoModel

interface RickAndMortyLocalSource {

    suspend fun insertAll(characters: List<Character>)

    suspend fun getAllCharacters(): Result<CharactersPagination>

    suspend fun getPaginationInfo(): InfoModel

    suspend fun savePaginationInfo(infoModel: InfoModel)
}

class RickAndMortyLocalSourceImpl(
    private val dao: CharacterDao,
    private val mapper: CharactersLocalMapper,
    private val paginationLocalSource: PaginationLocalSource
) : RickAndMortyLocalSource {

    override suspend fun insertAll(characters: List<Character>) {
        dao.insert(mapper.transformToEntities(characters))
    }

    override suspend fun getAllCharacters() = Result.success(
        CharactersPagination(
            info = paginationLocalSource.getPaginationInfo(),
            characters = mapper.transform(dao.getAllCharacters())
        )
    )

    override suspend fun getPaginationInfo(): InfoModel = paginationLocalSource.getPaginationInfo()

    override suspend fun savePaginationInfo(infoModel: InfoModel) {
        paginationLocalSource.savePaginationInfo(infoModel.pages, infoModel.next)
    }
}