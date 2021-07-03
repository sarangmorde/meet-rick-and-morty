package com.meetrickandmorty.app.ui.characters

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.meetrickandmorty.domain.model.Character
import com.meetrickandmorty.domain.model.CharactersPagination
import com.meetrickandmorty.domain.model.InfoModel
import com.meetrickandmorty.domain.model.Location
import com.meetrickandmorty.domain.model.Origin
import com.meetrickandmorty.domain.usecase.GetAllCharactersUseCase
import com.meetrickandmorty.domain.utils.Constants.EMPTY_STRING
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
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
    lateinit var data: CharactersData

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
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun getAllCharactersSuccessfulTest() {
        runBlockingTest {
            whenever(useCase.getAllCharacters(any(), any())).thenReturn(
                Result.success(characterPaginationMock)
            )

            viewModel = CharactersViewModel(data, useCase)
            verify(useCase).getAllCharacters(1)
            verify(data).toggleShowLoading(false)
            verify(data).setCharacters(characterPaginationMock.characters)
            verify(data).setPaginationInfo(characterPaginationMock.info)
        }
    }

    @Test
    fun getAllCharactersFailureTest() {
        runBlockingTest {
            whenever(useCase.getAllCharacters(any(), any())).thenReturn(
                Result.failure(Throwable(EMPTY_STRING))
            )

            viewModel = CharactersViewModel(data, useCase)
            verify(useCase).getAllCharacters(1, EMPTY_STRING)
            verify(data).toggleShowLoading(false)
            verify(data).setCharactersFailure(EMPTY_STRING)
        }
    }

    @Test
    fun `loadNextPage should call the api`() {
        runBlockingTest {
            whenever(useCase.getAllCharacters(any(), any())).thenReturn(
                Result.success(characterPaginationMock)
            )

            viewModel = CharactersViewModel(data, useCase)
            viewModel.loadNextPage(5, characterPaginationMock.info)
            verify(useCase).getAllCharacters(5)
            verify(data, times(2)).toggleShowLoading(false)
            verify(data, times(2)).setCharacters(any())
            verify(data, times(2)).setPaginationInfo(characterPaginationMock.info)
        }
    }

    @Test
    fun `loadNextPage should call the api with page 1 when info is null`() {
        runBlockingTest {
            whenever(useCase.getAllCharacters(any(), any())).thenReturn(
                Result.success(characterPaginationMock)
            )

            viewModel = CharactersViewModel(data, useCase)
            viewModel.loadNextPage(5, null)
            verify(useCase, times(2)).getAllCharacters(1)
            verify(data, times(2)).toggleShowLoading(false)
            verify(data, times(2)).setCharacters(any())
            verify(data, times(2)).setPaginationInfo(characterPaginationMock.info)
        }
    }

    @Test
    fun `loadNextPage should not call the api when filtering`() {
        runBlockingTest {
            whenever(useCase.getAllCharacters(any(), any())).thenReturn(
                Result.success(characterPaginationMock)
            )

            viewModel = CharactersViewModel(data, useCase)
            viewModel.filterCharacters(QUERY)
            viewModel.loadNextPage(1, characterPaginationMock.info)
            verify(useCase).getAllCharacters(1, QUERY)
            verify(data, times(2)).toggleShowLoading(false)
            verify(data, times(2)).setCharacters(any())
            verify(data, times(2)).setPaginationInfo(characterPaginationMock.info)
        }
    }

    @Test
    fun `filterCharacters Should not call the api when query is null`() {
        runBlockingTest {
            whenever(useCase.getAllCharacters(any(), any())).thenReturn(
                Result.success(characterPaginationMock)
            )

            viewModel = CharactersViewModel(data, useCase)
            viewModel.filterCharacters(null)
            verify(useCase, times(1)).getAllCharacters(any(), any())
        }
    }

    @Test
    fun getCharactersLiveDataTest() {
        runBlockingTest {
            whenever(useCase.getAllCharacters(any(), any())).thenReturn(
                Result.success(characterPaginationMock)
            )

            viewModel = CharactersViewModel(data, useCase)
            viewModel.getCharactersLiveData()
            verify(data).getCharactersLiveData()
        }
    }

    @Test
    fun getCharactersFailureDataTest() {
        runBlockingTest {
            whenever(useCase.getAllCharacters(any(), any())).thenReturn(
                Result.success(characterPaginationMock)
            )
            viewModel = CharactersViewModel(data, useCase)
            viewModel.getCharactersFailureLiveData()
            verify(data).getCharactersFailureLiveData()
        }
    }

    @Test
    fun getPaginationInfoLiveDataTest() {
        runBlockingTest {
            whenever(useCase.getAllCharacters(any(), any())).thenReturn(
                Result.success(characterPaginationMock)
            )
            viewModel = CharactersViewModel(data, useCase)
            viewModel.getPaginationInfoLiveData()
            verify(data).getPaginationInfoLiveData()
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    companion object {
        private const val QUERY = "rick"
    }
}