package com.cristo17.dbapp.ui.screens.character_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristo17.dbapp.data.repository.CharacterRepository
import com.cristo17.dbapp.domain.model.CharacterItem
import kotlinx.coroutines.launch

class CharacterListViewModel : ViewModel() {
    private val repository = CharacterRepository()

    var characters by mutableStateOf<List<CharacterItem>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    init {
        loadCharacters()
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            isLoading = true
            try {
                characters = repository.getCharacters()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }
}