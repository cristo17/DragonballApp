package com.cristo17.dbapp.domain.model

data class CharacterDetail(
    val id: Int,
    val name: String,
    val ki: String,
    val maxKi: String,
    val race: String,
    val gender: String,
    val description: String,
    val imageUrl: String,
    val affiliation: String?,
    val originPlanet: String?,
    val abilities: List<String> = emptyList()
)