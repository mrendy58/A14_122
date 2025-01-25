package com.example.c14pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c14pam.model.Reservasi
import com.example.c14pam.repository.ResevRepository
import kotlinx.coroutines.launch
import java.io.IOException

class InsertResViewModel(private val resevRepository: ResevRepository) : ViewModel() {

    var insertResUiState by mutableStateOf(InsertResUiState())
        private set

    fun updateInsertResState(insertResUiEvent: InsertResUiEvent) {
        insertResUiState = InsertResUiState(insertResUiEvent = insertResUiEvent)
    }

    fun insertResev() {
        viewModelScope.launch {
            try {
                val reservasi = insertResUiState.insertResUiEvent.toResev()
                resevRepository.insertResev(reservasi)
                println("Data berhasil disimpan: $reservasi")
            } catch (e: IOException) {
                println("Gagal menyimpan data: ${e.message}")
                e.printStackTrace()
            } catch (e: Exception) {
                println("Terjadi kesalahan: ${e.message}")
                e.printStackTrace()
            }
        }
    }
}

data class InsertResUiState(
    val insertResUiEvent: InsertResUiEvent = InsertResUiEvent()
)

data class InsertResUiEvent(
    val id_reservasi: String = "",
    val id_villa: String = "",
    val id_pelanggan: String = "",
    val check_in: String = "",
    val check_out: String = "",
    val jumlah_kamar: String = ""
)

fun InsertResUiEvent.toResev(): Reservasi = Reservasi(
    id_reservasi = id_reservasi,
    id_villa = id_villa,
    id_pelanggan = id_pelanggan,
    check_in = check_in,
    check_out = check_out,
    jumlah_kamar = jumlah_kamar
)

fun Reservasi.toUiStateRes(): InsertResUiState = InsertResUiState(
    insertResUiEvent = toInsertResUiEvent()
)

fun Reservasi.toInsertResUiEvent(): InsertResUiEvent = InsertResUiEvent(
    id_reservasi = id_reservasi,
    id_villa = id_villa,
    id_pelanggan = id_pelanggan,
    check_in = check_in,
    check_out = check_out,
    jumlah_kamar = jumlah_kamar
)