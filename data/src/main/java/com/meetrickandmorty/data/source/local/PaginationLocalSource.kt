package com.meetrickandmorty.data.source.local

import android.app.Application
import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import com.meetrickandmorty.domain.model.InfoModel
import com.meetrickandmorty.domain.utils.Constants.EMPTY_STRING

interface PaginationLocalSource {

    suspend fun savePaginationInfo(pages: Int, next: String)

    suspend fun getPaginationInfo(): InfoModel
}

class PaginationLocalSourceImpl(
    application: Application
) : PaginationLocalSource {

    private val prefs = application.getSharedPreferences(PREFERENCES, MODE_PRIVATE)

    override suspend fun savePaginationInfo(pages: Int, next: String) {
        prefs.edit {
            putInt(KEY_PAGES, pages)
            putString(KEY_NEXT, next)
        }
    }

    override suspend fun getPaginationInfo(): InfoModel = InfoModel(
        count = 0,
        next = prefs.getString(KEY_NEXT, EMPTY_STRING) ?: EMPTY_STRING,
        pages = prefs.getInt(KEY_PAGES, 1),
        prev = EMPTY_STRING
    )

    companion object {
        private const val PREFERENCES = "RickAndMortyPreferences"
        private const val KEY_PAGES = "pages"
        private const val KEY_NEXT = "next"

    }
}