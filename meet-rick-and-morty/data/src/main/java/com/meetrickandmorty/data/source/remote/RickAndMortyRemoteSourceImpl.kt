package com.meetrickandmorty.data.source.remote

import com.meetrickandmorty.data.mapper.CharactersRemoteMapper
import com.meetrickandmorty.data.source.remote.api.RickAndMortyApi
import com.meetrickandmorty.domain.model.CharactersPagination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class RickAndMortyRemoteSourceImpl(
    private val api: RickAndMortyApi,
    private val mapper: CharactersRemoteMapper
) : RickAndMortyRemoteSource {

    override suspend fun getAllCharacters(offset: Int): Result<CharactersPagination> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getAllCharacters(offset)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    return@withContext Result.success(
                        mapper.transform(responseBody)
                    )
                }
            } catch (e: IOException) {
                return@withContext Result.failure(e)
            } catch (e: HttpException) {
                return@withContext Result.failure(e)
            } catch (t: Throwable) {
                return@withContext Result.failure(t)
            }
            return@withContext Result.failure(Throwable())
        }
    }
}