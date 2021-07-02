package com.meetrickandmorty.data.repository

import com.meetrickandmorty.data.source.remote.RickAndMortyRemoteSource
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class RickAndMortyRepositoryImplTest {

    @Mock
    lateinit var remoteSource: RickAndMortyRemoteSource

    @InjectMocks
    lateinit var repository: RickAndMortyRepositoryImpl

    @Test
    fun getCharactersList() {
        runBlocking {
            repository.getAllCharacters(0)
            verify(remoteSource).getAllCharacters(0)
        }
    }
}