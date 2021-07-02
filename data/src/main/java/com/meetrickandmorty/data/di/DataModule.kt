package com.meetrickandmorty.data.di

import com.meetrickandmorty.data.BuildConfig
import com.meetrickandmorty.data.mapper.CharactersRemoteMapper
import com.meetrickandmorty.data.mapper.CharactersRemoteMapperImpl
import com.meetrickandmorty.data.repository.RickAndMortyRepositoryImpl
import com.meetrickandmorty.data.source.remote.RickAndMortyRemoteSource
import com.meetrickandmorty.data.source.remote.RickAndMortyRemoteSourceImpl
import com.meetrickandmorty.data.source.remote.api.RickAndMortyApi
import com.meetrickandmorty.domain.repository.RickAndMortyRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val repositoryModule = module {
    single<RickAndMortyRepository> { RickAndMortyRepositoryImpl(remoteSource = get()) }
}

val sourceModule = module {
//    single<LoginDataSource> { LoginDataSourceImpl(mapper = get(), userDao = get()) }
//    single<OfferDataSource> { OfferDataSourceImpl(offersMapper = get(), offersDao = get()) }

    single<RickAndMortyRemoteSource> {
        RickAndMortyRemoteSourceImpl(api = get(), mapper = get())
    }
}

val mapperModule = module {
    single<CharactersRemoteMapper> { CharactersRemoteMapperImpl() }
}

val networkModule = module {
    single {
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    factory { get<Retrofit>().create(RickAndMortyApi::class.java) }
}
