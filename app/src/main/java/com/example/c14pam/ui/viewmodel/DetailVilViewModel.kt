package com.example.c14pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c14pam.model.Villa
import com.example.c14pam.repository.VillaRepository
import com.example.c14pam.ui.view.DestinasiVilDetail
import kotlinx.coroutines.launch

class DetailVilViewModel(
    savedStateHandle: SavedStateHandle,
    private val villaRepository: VillaRepository
) : ViewModel() {
    private val id_villa: String = checkNotNull(savedStateHandle[DestinasiVilDetail.ID_VILLA])

    var detailVilUiState: DetailVilUiState by mutableStateOf(DetailVilUiState())
        private set

    init {
        getVillaById()
    }

    private fun getVillaById() {
        viewModelScope.launch {
            detailVilUiState = DetailVilUiState(isLoading = true)
            try {
                val result = villaRepository.getVillaById(id_villa)
                detailVilUiState = DetailVilUiState(
                    detailVilUiEvent = result.toDetailVilUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailVilUiState = DetailVilUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
}

data class DetailVilUiState(
    val detailVilUiEvent: InsertVilUiEvent = InsertVilUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiEventEmpty: Boolean
        get() = detailVilUiEvent == InsertVilUiEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailVilUiEvent != InsertVilUiEvent()
}

fun Villa.toDetailVilUiEvent(): InsertVilUiEvent{
    return InsertVilUiEvent(
        id_villa = id_villa,
        nama_villa = nama_villa,
        alamat = alamat,
        kamar_tersedia = kamar_tersedia
    )
}