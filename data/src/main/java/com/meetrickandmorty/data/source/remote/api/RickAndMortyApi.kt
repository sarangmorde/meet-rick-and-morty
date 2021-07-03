package com.meetrickandmorty.data.source.remote.api

import com.meetrickandmorty.data.source.remote.response.CharacterListResponse
import com.meetrickandmorty.domain.utils.Constants.EMPTY_STRING
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") offset: Int,
        @Query("name") name: String = EMPTY_STRING
    ): Response<CharacterListResponse>
}