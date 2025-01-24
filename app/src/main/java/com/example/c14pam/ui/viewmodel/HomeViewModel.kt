package com.example.c14pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.c14pam.model.Villa
import com.example.c14pam.repository.VillaRepository
import kotlinx.coroutines.launch
import java.io.IOException

// Sealed class HomeUiState untuk merepresentasikan state UI pada halaman home.
sealed class HomeUiState {
    // Subclass Success: Menunjukkan bahwa data berhasil diambil dan berisi daftar villa.
    data class Success(val villa: List<Villa>) : HomeUiState()

    // Subclass Error: Menunjukkan bahwa terjadi kesalahan saat mengambil data.
    object Error : HomeUiState()

    // Subclass Loading: Menunjukkan bahwa aplikasi sedang dalam proses memuat data.
    object Loading : HomeUiState()
}

// ViewModel untuk halaman home yang bertanggung jawab mengelola data dan state UI.
class HomeViewModel(private val vla: VillaRepository) : ViewModel() {

    // State UI untuk halaman home. Default-nya adalah Loading.
    var vlaUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set // Hanya dapat diubah dari dalam ViewModel.

    // Fungsi init akan dipanggil saat ViewModel dibuat. Memuat data villa saat pertama kali diinisialisasi.
    init {
        getVla()
    }

    // Fungsi untuk mengambil data villa dari repository.
    fun getVla() {
        viewModelScope.launch {
            vlaUiState = HomeUiState.Loading // Set state ke Loading sebelum memulai proses.
            vlaUiState = try {
                // Mengambil data villa dari repository dan mengubah state ke Success.
                HomeUiState.Success(vla.getVilla())
            } catch (e: IOException) {
                // Jika terjadi IOException (misalnya, masalah jaringan), set state ke Error.
                HomeUiState.Error
            } catch (e: HttpException) {
                // Jika terjadi HttpException (misalnya, respons API gagal), set state ke Error.
                HomeUiState.Error
            }
        }
    }

    // Fungsi untuk menghapus villa berdasarkan ID.
    fun deleteVla(id_villa: String) {
        viewModelScope.launch {
            try {
                // Menghapus villa dari repository.
                vla.deleteVilla(id_villa)
                // Memuat ulang data setelah penghapusan berhasil.
                getVla()
            } catch (e: IOException) {
                // Jika terjadi IOException, set state ke Error.
                HomeUiState.Error
            } catch (e: HttpException) {
                // Jika terjadi HttpException, set state ke Error.
                HomeUiState.Error
            }
        }
    }
}