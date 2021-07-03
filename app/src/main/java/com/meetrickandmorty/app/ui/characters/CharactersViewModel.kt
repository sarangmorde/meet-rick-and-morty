package com.meetrickandmorty.app.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.meetrickandmorty.app.ui.base.BaseViewModel
import com.meetrickandmorty.domain.model.Character
import com.meetrickandmorty.domain.model.InfoModel
import com.meetrickandmorty.domain.usecase.GetAllCharactersUseCase
import com.meetrickandmorty.domain.utils.Constants.EMPTY_STRING
import kotlinx.coroutines.launch

class CharactersViewModel(
    data: CharactersData,
    private val useCase: GetAllCharactersUseCase
) : BaseViewModel<CharactersData>(data) {

    private var isFilteringCharacters = false
    private val characters: MutableList<Character> = mutableListOf()

    init {
        getAllCharacters()
    }

    fun getCharactersLiveData(): LiveData<List<Character>> = data.getCharactersLiveData()

    fun getCharactersFailureLiveData(): LiveData<String> = data.getCharactersFailureLiveData()

    fun getPaginationInfoLiveData(): LiveData<InfoModel> = data.getPaginationInfoLiveData()

    private fun getAllCharacters(page: Int = DEFAULT_PAGE, name: String = EMPTY_STRING) {
        viewModelScope.launch {
            data.toggleShowLoading(true)
            useCase.getAllCharacters(page, name).fold(
                {
                    data.toggleShowLoading(false)
                    if (isFilteringCharacters) {
                        data.setCharacters(it.characters)
                    } else {
                        characters.addAll(it.characters)
                        data.setCharacters(characters)
                    }
                    data.setPaginationInfo(it.info)
                },
                {
                    data.toggleShowLoading(false)
                    data.setCharactersFailure(it.message)
                }
            )
        }
    }

    fun loadNextPage(nextPage: Int, info: InfoModel?) {
        if (isFilteringCharacters) return

        if (info == null) {
            getAllCharacters()
        } else {
            if (nextPage <= info.pages && info.next.isNotEmpty()) {
                getAllCharacters(nextPage)
            }
        }
    }

    fun filterCharacters(query: String?) {
        if (query.isNullOrEmpty()) {
            isFilteringCharacters = false
            data.setCharacters(characters)
        } else {
            isFilteringCharacters = true
            getAllCharacters(name = query)
        }
    }

    companion object {
        private const val DEFAULT_PAGE = 1
    }
}