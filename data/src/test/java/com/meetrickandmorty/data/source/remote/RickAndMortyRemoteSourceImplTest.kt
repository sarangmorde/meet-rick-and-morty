package com.meetrickandmorty.data.source.remote

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.Gson
import com.meetrickandmorty.data.CharacterListMockData
import com.meetrickandmorty.data.mapper.CharactersRemoteMapper
import com.meetrickandmorty.data.source.remote.api.RickAndMortyApi
import com.meetrickandmorty.data.source.remote.response.CharacterListResponse
import com.meetrickandmorty.domain.model.CharactersPagination
import com.meetrickandmorty.domain.model.InfoModel
import com.meetrickandmorty.domain.utils.Constants.EMPTY_STRING
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class RickAndMortyRemoteSourceImplTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var api: RickAndMortyApi

    @Mock
    lateinit var mapper: CharactersRemoteMapper

    @InjectMocks
    lateinit var remoteSource: RickAndMortyRemoteSourceImpl

    private val characterPaginationMock = CharactersPagination(
        info = InfoModel(
            0, EMPTY_STRING, 0, EMPTY_STRING
        ),
        emptyList()
    )

    @Test
    fun getAllCharactersWithSuccessTest() {
        runBlocking {
            val charactersResponse = Gson().fromJson(
                CharacterListMockData.getCharacterList(),
                CharacterListResponse::class.java
            )
            whenever(api.getAllCharacters(any(), any())).thenReturn(Response.success(charactersResponse))
            whenever(mapper.transform(charactersResponse)).thenReturn(characterPaginationMock)

            val result = remoteSource.getAllCharacters(0, EMPTY_STRING)
            verify(api).getAllCharacters(0)
            verify(mapper).transform(charactersResponse)
            assertEquals(result.getOrNull(), characterPaginationMock)
        }
    }

    @Test
    fun getAllCharactersWithFailureTest() {
        runBlocking {
            whenever(api.getAllCharacters(any(), any())).thenThrow(RuntimeException())

            val result = remoteSource.getAllCharacters(0, EMPTY_STRING)
            verify(api).getAllCharacters(0)
            verify(mapper, never()).transform(any())
            assertTrue(result.isFailure)
        }
    }
}