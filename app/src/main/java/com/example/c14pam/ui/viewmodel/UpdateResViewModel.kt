package com.example.c14pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c14pam.model.Reservasi
import com.example.c14pam.repository.ResevRepository
import com.example.c14pam.ui.view.DestinasiResUpdate
import kotlinx.coroutines.launch

class UpdateResViewModel (
    savedStateHandle: SavedStateHandle,
    private val rsv: ResevRepository
): ViewModel(){
    var updateResUiState by mutableStateOf(InsertResUiState())
        private set

    private val _idReservasi: String = checkNotNull(savedStateHandle[DestinasiResUpdate.ID_RESERVASI])

    init {
        viewModelScope.launch {
            updateResUiState = rsv.getResevById(_idReservasi)
                .toUiStateRes()
        }
    }

    fun updateInsertResState(insertResUiEvent: InsertResUiEvent){
        updateResUiState = InsertResUiState(insertResUiEvent = insertResUiEvent)
    }

    suspend fun updateRes(){
        viewModelScope.launch {
            try {
                rsv.updateResev(_idReservasi, updateResUiState.insertResUiEvent.toResev())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}
fun Reservasi.toUIStateRes(): InsertResUiState = InsertResUiState(
    insertResUiEvent = this.toDetailResUiEvent(),
)