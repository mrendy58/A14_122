package com.example.c14pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c14pam.model.Pelanggan
import com.example.c14pam.repository.PelangganRepository
import com.example.c14pam.ui.view.DestinasiPelUpdate
import kotlinx.coroutines.launch

class UpdatePelViewModel(
    savedStateHandle: SavedStateHandle,
    private val pelangganRepository: PelangganRepository
) : ViewModel() {
    var updatePelUiState by mutableStateOf(InsertPelUiState())
        private set

    private val _idPelanggan: String = checkNotNull(savedStateHandle[DestinasiPelUpdate.ID_PELANGGAN])

    init {
        viewModelScope.launch {
            updatePelUiState = pelangganRepository.getPelById(_idPelanggan)
                .toUiStatePel()
        }
    }

    fun updateInsertPelState(insertPelUiEvent: InsertPelUiEvent) {
        updatePelUiState = InsertPelUiState(insertPelUiEvent = insertPelUiEvent)
    }

    suspend fun updatePelanggan() {
        viewModelScope.launch {
            try {
                pelangganRepository.updatePel(
                    _idPelanggan,
                    updatePelUiState.insertPelUiEvent.toPelanggan()
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

fun Pelanggan.toUIStatePel(): InsertPelUiState = InsertPelUiState(
    insertPelUiEvent = this.toInsertPelUiEvent()
)