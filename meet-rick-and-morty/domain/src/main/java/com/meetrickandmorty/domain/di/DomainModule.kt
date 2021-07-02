package com.meetrickandmorty.domain.di

import com.meetrickandmorty.domain.usecase.GetAllCharactersUseCase
import com.meetrickandmorty.domain.usecase.GetAllCharactersUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {

    factory<GetAllCharactersUseCase> { GetAllCharactersUseCaseImpl(repository = get()) }
}