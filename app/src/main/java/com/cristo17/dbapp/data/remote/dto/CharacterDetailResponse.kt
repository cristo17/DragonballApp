package com.cristo17.dbapp.data.remote.dto

data class CharacterDetailResponse(
    val id: Int,
    val name: String,
    val image: String,
    val ki: String,
    val maxKi: String,
    val race: String,
    val gender: String,
    val affiliation: String,
    val description: String,
    val originPlanet: OriginPlanetDto?,
    val transformations: List<TransformationDto>?
)

data class OriginPlanetDto(
    val id: Int,
    val name: String,
    val image: String,
    val description: String? = null
)

data class TransformationDto(
    val id: Int,
    val name: String,
    val image: String
)