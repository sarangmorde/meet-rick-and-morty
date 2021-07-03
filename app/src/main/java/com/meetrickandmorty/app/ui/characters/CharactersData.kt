package com.meetrickandmorty.app.ui.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.meetrickandmorty.app.ui.base.BaseData
import com.meetrickandmorty.domain.model.Character
import com.meetrickandmorty.domain.model.InfoModel

data class CharactersData(
    private val charactersFailure: MutableLiveData<String> = MutableLiveData(),
    private val charactersLiveData: MutableLiveData<List<Character>> = MutableLiveData(),
    private val paginationInfo: MutableLiveData<InfoModel> = MutableLiveData(),
    val showLoading: MutableLiveData<Boolean> = MutableLiveData(false)
) : BaseData() {

    internal fun getCharactersLiveData(): LiveData<List<Character>> = charactersLiveData

    internal fun getCharactersFailureLiveData(): LiveData<String> = charactersFailure

    internal fun getPaginationInfoLiveData(): LiveData<InfoModel> = paginationInfo

    internal fun toggleShowLoading(show: Boolean) {
        showLoading.postValue(show)
    }

    internal fun setCharacters(characters: List<Character>) {
        charactersLiveData.postValue(characters)
    }

    internal fun setCharactersFailure(message: String?) {
        charactersFailure.postValue(message)
    }

    internal fun setPaginationInfo(info: InfoModel) {
        paginationInfo.postValue(info)
    }
}
