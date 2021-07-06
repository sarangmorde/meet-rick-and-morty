package com.meetrickandmorty.data.di

import androidx.room.Room
import com.meetrickandmorty.data.BuildConfig
import com.meetrickandmorty.data.database.RickAndMortyDatabase
import com.meetrickandmorty.data.mapper.CharactersLocalMapper
import com.meetrickandmorty.data.mapper.CharactersLocalMapperImpl
import com.meetrickandmorty.data.mapper.CharactersRemoteMapper
import com.meetrickandmorty.data.mapper.CharactersRemoteMapperImpl
import com.meetrickandmorty.data.repository.RickAndMortyRepositoryImpl
import com.meetrickandmorty.data.source.local.PaginationLocalSource
import com.meetrickandmorty.data.source.local.PaginationLocalSourceImpl
import com.meetrickandmorty.data.source.local.RickAndMortyLocalSource
import com.meetrickandmorty.data.source.local.RickAndMortyLocalSourceImpl
import com.meetrickandmorty.data.source.remote.RickAndMortyRemoteSource
import com.meetrickandmorty.data.source.remote.RickAndMortyRemoteSourceImpl
import com.meetrickandmorty.data.source.remote.api.RickAndMortyApi
import com.meetrickandmorty.domain.repository.RickAndMortyRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val repositoryModule = module {
    single<RickAndMortyRepository> {
        RickAndMortyRepositoryImpl(
            remoteSource = get(),
            localSource = get()
        )
    }
}

val sourceModule = module {
    single<RickAndMortyRemoteSource> {
        RickAndMortyRemoteSourceImpl(api = get(), mapper = get())
    }
    single<RickAndMortyLocalSource> {
        RickAndMortyLocalSourceImpl(dao = get(), mapper = get(), paginationLocalSource = get())
    }
    single<PaginationLocalSource> {
        PaginationLocalSourceImpl(application = get())
    }
}

val mapperModule = module {
    single<CharactersRemoteMapper> { CharactersRemoteMapperImpl() }
    single<CharactersLocalMapper> { CharactersLocalMapperImpl() }
}

val networkModule = module {
    single {
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    factory { get<Retrofit>().create(RickAndMortyApi::class.java) }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            RickAndMortyDatabase::class.java,
            "RickAndMortyDatabase"
        ).build()
    }
    single { get<RickAndMortyDatabase>().characterDao() }
}