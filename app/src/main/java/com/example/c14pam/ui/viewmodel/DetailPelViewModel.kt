package com.example.c14pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c14pam.model.Pelanggan
import com.example.c14pam.repository.PelangganRepository
import com.example.c14pam.ui.view.DestinasiDetailPel
import kotlinx.coroutines.launch

class DetailPelViewModel(
    savedStateHandle: SavedStateHandle,
    private val pelangganRepository: PelangganRepository
) : ViewModel() {
    private val id_pelanggan: String = checkNotNull(savedStateHandle[DestinasiDetailPel.ID_PELANGGAN])

    var detailPelUiState: DetailPelUiState by mutableStateOf(DetailPelUiState())
        private set

    init {
        getPelangganById()
    }

    private fun getPelangganById() {
        viewModelScope.launch {
            detailPelUiState = DetailPelUiState(isLoading = true)
            try {
                val result = pelangganRepository.getPelById(id_pelanggan)
                detailPelUiState = DetailPelUiState(
                    detailPelUiEvent = result.toDetailPelUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailPelUiState = DetailPelUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
}

data class DetailPelUiState(
    val detailPelUiEvent: InsertPelUiEvent = InsertPelUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailPelUiEvent == InsertPelUiEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailPelUiEvent != InsertPelUiEvent()
}

fun Pelanggan.toDetailPelUiEvent(): InsertPelUiEvent {
    return InsertPelUiEvent(
        id_pelanggan = id_pelanggan,
        nama_pelanggan = nama_pelanggan,
        no_hp = no_hp
    )
}