package com.meetrickandmorty.domain.model

data class CharactersPagination(
    val info: InfoModel,
    val characters: List<Character>
)
