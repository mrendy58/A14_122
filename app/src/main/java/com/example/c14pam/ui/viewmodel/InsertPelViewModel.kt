package com.example.c14pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c14pam.model.Pelanggan
import com.example.c14pam.repository.PelangganRepository
import kotlinx.coroutines.launch
import java.io.IOException

class InsertPelViewModel(private val pelangganRepository: PelangganRepository) : ViewModel() {

    var insertPelUiState by mutableStateOf(InsertPelUiState())
        private set

    fun updateInsertPelState(insertPelUiEvent: InsertPelUiEvent) {
        insertPelUiState = InsertPelUiState(insertPelUiEvent = insertPelUiEvent)
    }

    fun insertPelanggan() {
        viewModelScope.launch {
            try {
                val pelanggan = insertPelUiState.insertPelUiEvent.toPelanggan()
                pelangganRepository.insertPel(pelanggan)
                println("Data berhasil disimpan: $pelanggan")
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

data class InsertPelUiState(
    val insertPelUiEvent: InsertPelUiEvent = InsertPelUiEvent()
)

data class InsertPelUiEvent(
    val id_pelanggan: String = "",
    val nama_pelanggan: String = "",
    val no_hp: String = ""
)

fun InsertPelUiEvent.toPelanggan(): Pelanggan = Pelanggan(
    id_pelanggan = id_pelanggan,
    nama_pelanggan = nama_pelanggan,
    no_hp = no_hp
)

fun Pelanggan.toUiStatePel(): InsertPelUiState = InsertPelUiState(
    insertPelUiEvent = toInsertPelUiEvent()
)

fun Pelanggan.toInsertPelUiEvent(): InsertPelUiEvent = InsertPelUiEvent(
    id_pelanggan = id_pelanggan,
    nama_pelanggan = nama_pelanggan,
    no_hp = no_hp
)