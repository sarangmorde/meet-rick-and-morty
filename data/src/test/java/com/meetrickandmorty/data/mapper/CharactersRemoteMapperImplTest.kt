package com.meetrickandmorty.data.mapper

import com.google.gson.Gson
import com.meetrickandmorty.data.CharacterListMockData
import com.meetrickandmorty.data.source.remote.response.CharacterListResponse
import com.meetrickandmorty.domain.utils.Constants.EMPTY_STRING
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

internal class CharactersRemoteMapperImplTest {

    private lateinit var characterListMapperImpl: CharactersRemoteMapperImpl

    @Before
    fun setUp() {
        characterListMapperImpl = CharactersRemoteMapperImpl()
    }

    @Test
    fun transformWithValidInputTest() {

        val charactersResponse = Gson().fromJson(
            CharacterListMockData.getCharacterList(),
            CharacterListResponse::class.java
        )
        val result = characterListMapperImpl.transform(charactersResponse)

        assertNotNull(result)
        charactersResponse.results?.let {
            assertEquals(it.size, result.characters.size)

            val expectedCharacter = it[0]
            val resultCharacter = result.characters[0]
            assertEquals(expectedCharacter.id, resultCharacter.id)
            assertEquals(expectedCharacter.name, resultCharacter.name)
            assertEquals(expectedCharacter.gender, resultCharacter.gender)
            assertEquals(expectedCharacter.species, resultCharacter.species)
            assertEquals(expectedCharacter.origin?.name, resultCharacter.origin.name)
            assertEquals(expectedCharacter.origin?.url, resultCharacter.origin.url)
            assertEquals(expectedCharacter.location?.name, resultCharacter.location.name)
            assertEquals(expectedCharacter.location?.url, resultCharacter.location.url)
            assertEquals(expectedCharacter.type, resultCharacter.type)
            assertEquals(expectedCharacter.created, resultCharacter.created)
            assertEquals(expectedCharacter.status, resultCharacter.status)
        }

        charactersResponse.info?.let {
            assertEquals(it.count, result.info.count)
            assertEquals(it.next, result.info.next)
            assertEquals(it.pages, result.info.pages)
            assertEquals(it.prev.orEmpty(), result.info.prev)
        }
    }

    @Test
    fun transformWithNullInputTest() {

        val result = characterListMapperImpl.transform(CharacterListResponse(null, null))

        assertNotNull(result)
        assertEquals(0, result.characters.size)
        assertEquals(0, result.info.count)
        assertEquals(EMPTY_STRING, result.info.next)
        assertEquals(0, result.info.pages)
        assertEquals(EMPTY_STRING, result.info.prev)
    }
}