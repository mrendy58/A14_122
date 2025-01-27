package com.example.c14pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.c14pam.model.Pelanggan
import com.example.c14pam.repository.PelangganRepository
import kotlinx.coroutines.launch
import java.io.IOException

// Sealed class PelangganUiState untuk merepresentasikan state UI pada halaman pelanggan.
sealed class PelangganUiState {
    // Subclass Success: Menunjukkan bahwa data pelanggan berhasil diambil dan berisi daftar pelanggan.
    data class Success(val pelanggan: List<Pelanggan>) : PelangganUiState()

    // Subclass Error: Menunjukkan bahwa terjadi kesalahan saat mengambil data pelanggan.
    object Error : PelangganUiState()

    // Subclass Loading: Menunjukkan bahwa aplikasi sedang dalam proses memuat data pelanggan.
    object Loading : PelangganUiState()
}

// ViewModel untuk halaman pelanggan yang bertanggung jawab mengelola data dan state UI.
class PelangganViewModel(private val plg: PelangganRepository) : ViewModel() {

    // State UI untuk halaman pelanggan. Default-nya adalah Loading.
    var plgUiState: PelangganUiState by mutableStateOf(PelangganUiState.Loading)
        private set // Hanya dapat diubah dari dalam ViewModel.

    // Fungsi init akan dipanggil saat ViewModel dibuat. Memuat data pelanggan saat pertama kali diinisialisasi.
    init {
        getPlg()
    }

    // Fungsi untuk mengambil data pelanggan dari repository.
    fun getPlg() {
        viewModelScope.launch {
            plgUiState = PelangganUiState.Loading // Set state ke Loading sebelum memulai proses.
            plgUiState = try {
                // Mengambil data pelanggan dari repository dan mengubah state ke Success.
                PelangganUiState.Success(plg.getPel())
            } catch (e: IOException) {
                // Jika terjadi IOException (misalnya, masalah jaringan), set state ke Error.
                PelangganUiState.Error
            } catch (e: HttpException) {
                // Jika terjadi HttpException (misalnya, respons API gagal), set state ke Error.
                PelangganUiState.Error
            }
        }
    }

    // Fungsi untuk menghapus pelanggan berdasarkan ID.
    fun deletePlg(id_pelanggan: String) {
        viewModelScope.launch {
            try {
                // Menghapus pelanggan dari repository.
                plg.deletePel(id_pelanggan)
                // Memuat ulang data setelah penghapusan berhasil.
                getPlg()
            } catch (e: IOException) {
                // Jika terjadi IOException, set state ke Error.
                PelangganUiState.Error
            } catch (e: HttpException) {
                // Jika terjadi HttpException, set state ke Error.
                PelangganUiState.Error
            }
        }
    }
}