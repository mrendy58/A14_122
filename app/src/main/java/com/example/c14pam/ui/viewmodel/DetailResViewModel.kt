package com.example.c14pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c14pam.model.Reservasi
import com.example.c14pam.repository.ResevRepository
import com.example.c14pam.ui.view.DestinasiDetailRes
import kotlinx.coroutines.launch

class DetailResViewModel(
    savedStateHandle: SavedStateHandle,
    private val resevRepository: ResevRepository
) : ViewModel() {
    private val id_reservasi: String = checkNotNull(savedStateHandle[DestinasiDetailRes.ID_RESERVASI])

    var detailResUiState: DetailResUiState by mutableStateOf(DetailResUiState())
        private set

    init {
        getResevById()
    }

    private fun getResevById() {
        viewModelScope.launch {
            detailResUiState = DetailResUiState(isLoading = true)
            try {
                val result = resevRepository.getResevById(id_reservasi)
                detailResUiState = DetailResUiState(
                    detailResUiEvent = result.toDetailResUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailResUiState = DetailResUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
}

data class DetailResUiState(
    val detailResUiEvent: InsertResUiEvent = InsertResUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiEventEmpty: Boolean
        get() = detailResUiEvent == InsertResUiEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailResUiEvent != InsertResUiEvent()
}

fun Reservasi.toDetailResUiEvent(): InsertResUiEvent{
    return InsertResUiEvent(
        id_reservasi = id_reservasi,
        id_villa = id_villa,
        id_pelanggan = id_pelanggan,
        check_in = check_in,
        check_out = check_out,
        jumlah_kamar = jumlah_kamar
    )
}