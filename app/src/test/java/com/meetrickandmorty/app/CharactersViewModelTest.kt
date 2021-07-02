package com.meetrickandmorty.app

import android.app.Application
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.meetrickandmorty.app.ui.characters.CharactersViewModel
import com.meetrickandmorty.app.utils.InternetUtil
import com.meetrickandmorty.domain.model.Character
import com.meetrickandmorty.domain.model.CharactersPagination
import com.meetrickandmorty.domain.model.InfoModel
import com.meetrickandmorty.domain.model.Location
import com.meetrickandmorty.domain.model.Origin
import com.meetrickandmorty.domain.usecase.GetAllCharactersUseCase
import com.meetrickandmorty.domain.utils.Constants.EMPTY_STRING
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class CharactersViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var application: Application

    @Mock
    lateinit var connectivityManager: ConnectivityManager

    @Mock
    lateinit var networkInfo: NetworkInfo

    @Mock
    lateinit var useCase: GetAllCharactersUseCase

    private lateinit var viewModel: CharactersViewModel

    private val characterPaginationMock = CharactersPagination(
        info = InfoModel(
            0, "https://rickandmortyapi.com/api/character/?page=2", 10, EMPTY_STRING
        ),
        listOf(
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
    )

    @Before
    fun setup() {
        whenever(application.getSystemService(any())).thenReturn(connectivityManager)
        whenever(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)
        whenever(networkInfo.isConnectedOrConnecting).thenReturn(true)
        InternetUtil.init(application)
    }

    @Test
    fun getAllCharactersSuccessfulTest() {
        runBlockingTest {
            whenever(useCase.getAllCharacters(any())).thenReturn(
                Result.success(characterPaginationMock)
            )

            viewModel = CharactersViewModel(useCase)
            verify(useCase).getAllCharacters(1)
            assertEquals(false, viewModel.showLoading.value)
        }
    }

    @Test
    fun getAllCharactersFailureTest() {
        runBlockingTest {
            whenever(useCase.getAllCharacters(any())).thenReturn(
                Result.failure(Throwable(EMPTY_STRING))
            )

            viewModel = CharactersViewModel(useCase)
            verify(useCase).getAllCharacters(1)
            assertEquals(false, viewModel.showLoading.value)
            assertEquals(null, viewModel.getCharactersFailure.value)
        }
    }

    @Test
    fun loadNextPageTest() {
        runBlockingTest {
            whenever(useCase.getAllCharacters(any())).thenReturn(
                Result.success(characterPaginationMock)
            )

            viewModel = CharactersViewModel(useCase)
            viewModel.loadNextPage(1, characterPaginationMock.info)
            verify(useCase, times(2)).getAllCharacters(1)
            assertEquals(false, viewModel.showLoading.value)
        }
    }

    @Test
    fun filterCharactersTest() {
        runBlockingTest {
            whenever(useCase.getAllCharacters(any())).thenReturn(
                Result.success(characterPaginationMock)
            )

            viewModel = CharactersViewModel(useCase)
            val result = viewModel.filterCharacters("rick")
            assertEquals(1, result.size)
            assertEquals(characterPaginationMock.characters[0].name, result[0].name)
        }
    }
}