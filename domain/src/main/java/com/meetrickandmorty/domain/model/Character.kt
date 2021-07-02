package com.meetrickandmorty.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
) : Parcelable

@Parcelize
data class Location(
    val name: String,
    val url: String
) : Parcelable

@Parcelize
data class Origin(
    val name: String,
    val url: String
) : Parcelable