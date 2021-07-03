package com.meetrickandmorty.app.ui.characters

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.os.Looper
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.meetrickandmorty.domain.model.Character
import com.meetrickandmorty.domain.model.InfoModel
import com.meetrickandmorty.domain.model.Location
import com.meetrickandmorty.domain.model.Origin
import com.meetrickandmorty.domain.utils.Constants.EMPTY_STRING
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows

@RunWith(AndroidJUnit4::class)
class CharactersDataTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val data = CharactersData()

    private val charactersListMock = listOf(
        Character(
            created = EMPTY_STRING,
            name = "Rick Sanchez",
            gender = EMPTY_STRING,
            species = EMPTY_STRING,
            type = EMPTY_STRING,
            status = EMPTY_STRING,
            origin = Origin(EMPTY_STRING, EMPTY_STRING),
            location = Location(EMPTY_STRING, EMPTY_STRING),
            id = 1,
            episode = emptyList(),
            image = EMPTY_STRING,
            url = EMPTY_STRING,
        ),
        Character(
            created = EMPTY_STRING,
            name = "Morty Smith",
            gender = EMPTY_STRING,
            species = EMPTY_STRING,
            type = EMPTY_STRING,
            status = EMPTY_STRING,
            origin = Origin(EMPTY_STRING, EMPTY_STRING),
            location = Location(EMPTY_STRING, EMPTY_STRING),
            id = 2,
            episode = emptyList(),
            image = EMPTY_STRING,
            url = EMPTY_STRING,
        )
    )

    private val infoMock = InfoModel(
        0, "https://rickandmortyapi.com/api/character/?page=2", 10, EMPTY_STRING
    )

    @Test
    fun toggleShowLoadingTest() {
        data.toggleShowLoading(true)
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        assertTrue(data.showLoading.getOrAwaitValue() ?: false)
    }

    @Test
    fun setCharacters() {
        data.setCharacters(charactersListMock)
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        assertEquals(charactersListMock, data.getCharactersLiveData().getOrAwaitValue())
    }

    @Test
    fun setCharactersFailure() {
        val message = "error"
        data.setCharactersFailure(message)
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        assertEquals(message, data.getCharactersFailureLiveData().getOrAwaitValue())
    }

    @Test
    fun setPaginationInfo() {
        data.setPaginationInfo(infoMock)
        Shadows.shadowOf(Looper.getMainLooper()).idle()
        assertEquals(infoMock, data.getPaginationInfoLiveData().getOrAwaitValue())
    }
}