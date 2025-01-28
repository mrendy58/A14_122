package com.example.c14pam.ui.viewmodel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c14pam.model.Review
import com.example.c14pam.repository.ReviewRepository
import kotlinx.coroutines.launch
import java.io.IOException

class InsertRevViewModel(private val reviewRepository: ReviewRepository) : ViewModel() {

    // State untuk menyimpan data input dari UI
    var insertRevUiState by mutableStateOf(InsertRevUiState())
        private set

    // Fungsi untuk mengupdate state berdasarkan input dari UI
    fun updateInsertRevState(insertRevUiEvent: InsertRevUiEvent) {
        insertRevUiState = InsertRevUiState(insertRevUiEvent = insertRevUiEvent)
    }

    // Fungsi untuk menyimpan data review ke API
    fun insertReview() {
        viewModelScope.launch {
            try {
                // Konversi InsertRevUiEvent ke objek Review
                val review = insertRevUiState.insertRevUiEvent.toRev()
                // Panggil repository untuk menyimpan data
                reviewRepository.insertReview(review)
                println("Data berhasil disimpan: $review")
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
data class InsertRevUiState(
    val insertRevUiEvent: InsertRevUiEvent = InsertRevUiEvent()
)

// Data class untuk menyimpan event input dari UI
data class InsertRevUiEvent(
    val id_review: String = "",
    val id_reservasi: String = "",
    val nilai: String = "",
    val komentar: String = ""
)

// Ekstensi fungsi untuk mengkonversi InsertRevUiEvent ke objek Review
fun InsertRevUiEvent.toRev(): Review = Review(
    id_review = id_review,
    id_reservasi = id_reservasi,
    nilai = nilai,
    komentar = komentar
)


fun Review.toUiStateRev(): InsertRevUiState = InsertRevUiState(
    insertRevUiEvent = toInsertRevUiEvent()
)

fun Review.toInsertRevUiEvent(): InsertRevUiEvent = InsertRevUiEvent(
    id_review = id_review,
    id_reservasi = id_reservasi,
    nilai = nilai,
    komentar = komentar
)