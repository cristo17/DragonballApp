package com.cristo17.dbapp.data.remote.dto

data class CharacterListResponse(
    val items: List<CharacterDto>,
    val meta: MetaDto
)

data class MetaDto(
    val totalItems: Int,
    val itemCount: Int,
    val itemsPerPage: Int,
    val totalPages: Int,
    val currentPage: Int
)

data class CharacterDto(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val ki: String,
    val maxKi: String,
    val race: String,
    val gender: String,
    val affiliation: String
)