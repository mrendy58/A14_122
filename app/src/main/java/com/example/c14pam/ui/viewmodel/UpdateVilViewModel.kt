package com.example.c14pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c14pam.model.Villa
import com.example.c14pam.repository.VillaRepository
import com.example.c14pam.ui.view.DestinasiVilUpdate
import kotlinx.coroutines.launch

class UpdateVilViewModel (
    savedStateHandle: SavedStateHandle,
    private val vla: VillaRepository
): ViewModel(){
    var updateVilUiState by mutableStateOf(InsertVilUiState())
        private set

    private val _idVilla: String = checkNotNull(savedStateHandle[DestinasiVilUpdate.ID_VILLA])

    init {
        viewModelScope.launch {
            updateVilUiState = vla.getVillaById(_idVilla)
                .toUiStateVla()
        }
    }

    fun updateInsertVilState(insertUiEvent: InsertVilUiEvent){
        updateVilUiState = InsertVilUiState(insertVilUiEvent = insertUiEvent)
    }

    suspend fun updateVil(){
        viewModelScope.launch {
            try {
                vla.updateVilla(_idVilla, updateVilUiState.insertVilUiEvent.toVilla())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}
fun Villa.toUIStateVla(): InsertVilUiState = InsertVilUiState(
    insertVilUiEvent = this.toDetailVilUiEvent(),
)