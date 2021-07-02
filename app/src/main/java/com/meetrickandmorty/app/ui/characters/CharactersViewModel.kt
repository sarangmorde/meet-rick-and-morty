package com.meetrickandmorty.app.ui.characters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meetrickandmorty.app.utils.InternetUtil
import com.meetrickandmorty.domain.model.Character
import com.meetrickandmorty.domain.model.CharactersPagination
import com.meetrickandmorty.domain.model.InfoModel
import com.meetrickandmorty.domain.usecase.GetAllCharactersUseCase
import com.meetrickandmorty.domain.utils.Constants.EMPTY_STRING
import kotlinx.coroutines.launch

class CharactersViewModel(private val useCase: GetAllCharactersUseCase) : ViewModel() {

    val showLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    internal val getCharactersFailure: MutableLiveData<String> = MutableLiveData()
    internal val charactersResponse: MutableLiveData<CharactersPagination> = MutableLiveData()
    private val characters: MutableList<Character> = mutableListOf()

    private var nextPage = 1

    private val internetConnectivityObserver = Observer<Boolean> { isConnected ->
        if (isConnected) {
            getAllCharacters(nextPage)
        }
    }

    init {
        getAllCharacters(nextPage)
    }

    private fun getAllCharacters(page: Int) {
        viewModelScope.launch {
            if (InternetUtil.isInternetConnected()) {
                showLoading.postValue(true)
                useCase.getAllCharacters(page).fold(
                    {
                        showLoading.postValue(false)
                        characters.addAll(it.characters)
                        charactersResponse.postValue(it)
                    },
                    {
                        showLoading.postValue(false)
                        getCharactersFailure.postValue(it.message)
                    }
                )
            } else {
                getCharactersFailure.postValue(EMPTY_STRING)
                InternetUtil.observeForever(internetConnectivityObserver)
            }
        }
    }

    fun loadNextPage(nextPage: Int, info: InfoModel?) {
        info?.let {
            if (nextPage <= it.pages && it.next.isNotEmpty()) {
                this.nextPage = nextPage
                showLoading.postValue(true)
                getAllCharacters(nextPage)
            }
        }
    }

    fun filterCharacters(query: String?): List<Character> {
        return query?.let {
            return@let characters.filter {
                it.name.startsWith(query, ignoreCase = true)
            }.toList()
        } ?: characters
    }

    override fun onCleared() {
        super.onCleared()
        InternetUtil.removeObserver(internetConnectivityObserver)
    }
}