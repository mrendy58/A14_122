package com.example.c14pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.c14pam.model.Review
import com.example.c14pam.repository.ReviewRepository
import kotlinx.coroutines.launch
import java.io.IOException

// Sealed class ReviewUiState untuk merepresentasikan state UI pada halaman review.
sealed class ReviewUiState {
    // Subclass Success: Menunjukkan bahwa data review berhasil diambil dan berisi daftar review.
    data class Success(val review: List<Review>) : ReviewUiState()

    // Subclass Error: Menunjukkan bahwa terjadi kesalahan saat mengambil data review.
    object Error : ReviewUiState()

    // Subclass Loading: Menunjukkan bahwa aplikasi sedang dalam proses memuat data review.
    object Loading : ReviewUiState()
}

// ViewModel untuk halaman review yang bertanggung jawab mengelola data dan state UI.
class ReviewViewModel(private val rev: ReviewRepository) : ViewModel() {

    // State UI untuk halaman review. Default-nya adalah Loading.
    var revUiState: ReviewUiState by mutableStateOf(ReviewUiState.Loading)
        private set // Hanya dapat diubah dari dalam ViewModel.

    // Fungsi init akan dipanggil saat ViewModel dibuat. Memuat data review saat pertama kali diinisialisasi.
    init {
        getRev()
    }

    // Fungsi untuk mengambil data review dari repository.
    fun getRev() {
        viewModelScope.launch {
            revUiState = ReviewUiState.Loading // Set state ke Loading sebelum memulai proses.
            revUiState = try {
                // Mengambil data review dari repository dan mengubah state ke Success.
                ReviewUiState.Success(rev.getReview())
            } catch (e: IOException) {
                // Jika terjadi IOException (misalnya, masalah jaringan), set state ke Error.
                ReviewUiState.Error
            } catch (e: HttpException) {
                // Jika terjadi HttpException (misalnya, respons API gagal), set state ke Error.
                ReviewUiState.Error
            }
        }
    }

    // Fungsi untuk menghapus review berdasarkan ID.
    fun deleteRev(id_review: String) {
        viewModelScope.launch {
            try {
                // Menghapus review dari repository.
                rev.deleteReview(id_review)
                // Memuat ulang data setelah penghapusan berhasil.
                getRev()
            } catch (e: IOException) {
                // Jika terjadi IOException, set state ke Error.
                ReviewUiState.Error
            } catch (e: HttpException) {
                // Jika terjadi HttpException, set state ke Error.
                ReviewUiState.Error
            }
        }
    }
}