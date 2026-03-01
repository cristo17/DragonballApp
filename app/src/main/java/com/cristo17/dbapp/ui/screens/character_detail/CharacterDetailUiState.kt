package com.cristo17.dbapp.ui.screens.character_detail

import com.cristo17.dbapp.domain.model.CharacterDetail

sealed interface CharacterDetailUiState {
    object Loading : CharacterDetailUiState
    data class Success(val character: CharacterDetail) : CharacterDetailUiState
    data class Error(val message: String) : CharacterDetailUiState
}