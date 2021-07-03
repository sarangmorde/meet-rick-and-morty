package com.meetrickandmorty.app.di

import com.meetrickandmorty.app.ui.characters.CharactersData
import com.meetrickandmorty.app.ui.characters.CharactersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        CharactersViewModel(
            useCase = get(),
            data = CharactersData()
        )
    }
}