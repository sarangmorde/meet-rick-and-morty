package com.meetrickandmorty.domain.usecase

import com.meetrickandmorty.domain.repository.RickAndMortyRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class GetAllCharactersUseCaseImplTest {

    @Mock
    lateinit var repository: RickAndMortyRepository

    @InjectMocks
    lateinit var getAllCharactersUseCase: GetAllCharactersUseCaseImpl

    @Test
    fun getCharactersList() {
        runBlocking {
            getAllCharactersUseCase.getAllCharacters(0)
            verify(repository).getAllCharacters(0)
        }
    }
}