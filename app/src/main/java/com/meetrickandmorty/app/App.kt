package com.meetrickandmorty.app

import android.app.Application
import com.meetrickandmorty.app.di.presentationModule
import com.meetrickandmorty.app.utils.NetworkCheck
import com.meetrickandmorty.data.di.mapperModule
import com.meetrickandmorty.data.di.networkModule
import com.meetrickandmorty.data.di.repositoryModule
import com.meetrickandmorty.data.di.sourceModule
import com.meetrickandmorty.domain.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        NetworkCheck.init(this)
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    presentationModule,
                    useCaseModule,
                    repositoryModule,
                    sourceModule,
                    mapperModule,
                    networkModule
                )
            )
        }
    }
}