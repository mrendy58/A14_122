package com.example.c14pam.ui.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c14pam.model.Villa
import com.example.c14pam.repository.VillaRepository
import kotlinx.coroutines.launch
import java.io.IOException

class InsertVilViewModel(private val villaRepository: VillaRepository) : ViewModel() {

    // State untuk menyimpan data input dari UI
    var insertVilUiState by mutableStateOf(InsertVilUiState())
        private set

    // Fungsi untuk mengupdate state berdasarkan input dari UI
    fun updateInsertVilState(insertVilUiEvent: InsertVilUiEvent) {
        insertVilUiState = InsertVilUiState(insertVilUiEvent = insertVilUiEvent)
    }

    // Fungsi untuk menyimpan data villa ke API
    fun insertVilla() {
        viewModelScope.launch {
            try {
                // Konversi InsertVilUiEvent ke objek Villa
                val villa = insertVilUiState.insertVilUiEvent.toVilla()
                // Panggil repository untuk menyimpan data
                villaRepository.insertVilla(villa)
                println("Data berhasil disimpan: $villa")
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

// Data class untuk menyimpan state UI
data class InsertVilUiState(
    val insertVilUiEvent: InsertVilUiEvent = InsertVilUiEvent()
)

// Data class untuk menyimpan event input dari UI
data class InsertVilUiEvent(
    val id_villa: String = "",
    val nama_villa: String = "",
    val alamat: String = "",
    val kamar_tersedia: String = ""
)

// Ekstensi fungsi untuk mengkonversi InsertVilUiEvent ke objek Villa
fun InsertVilUiEvent.toVilla(): Villa = Villa(
    id_villa = id_villa,
    nama_villa = nama_villa,
    alamat = alamat,
    kamar_tersedia = kamar_tersedia
)


fun Villa.toUiStateVla(): InsertVilUiState = InsertVilUiState(
    insertVilUiEvent = toInsertVilUiEvent()
)

fun Villa.toInsertVilUiEvent(): InsertVilUiEvent = InsertVilUiEvent(
    id_villa = id_villa,
    nama_villa = nama_villa,
    alamat = alamat,
    kamar_tersedia = kamar_tersedia
)