package com.cristo17.dbapp.ui.screens.character_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristo17.dbapp.data.repository.CharacterRepository
import com.cristo17.dbapp.domain.model.CharacterDetail
import kotlinx.coroutines.launch

class CharacterDetailViewModel : ViewModel() {
    private val repository = CharacterRepository()

    var character by mutableStateOf<CharacterDetail?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun loadCharacter(id: Int) {
        viewModelScope.launch {
            isLoading = true
            try {
                character = repository.getCharacterDetail(id)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
}