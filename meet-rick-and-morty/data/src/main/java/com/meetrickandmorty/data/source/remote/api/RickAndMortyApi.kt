package com.meetrickandmorty.data.source.remote.api

import com.meetrickandmorty.data.source.remote.response.CharacterListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getAllCharacters(@Query("page") offset: Int): Response<CharacterListResponse>

    @GET("character/{characterId}")
    suspend fun getCharacterById(
        @Path("characterId") characterId: Long
    ): Response<CharacterListResponse>
}