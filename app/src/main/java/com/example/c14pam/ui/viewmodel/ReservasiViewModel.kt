package com.example.c14pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.c14pam.model.Reservasi
import com.example.c14pam.repository.ResevRepository
import kotlinx.coroutines.launch
import java.io.IOException

// Sealed class ResevUiState untuk merepresentasikan state UI pada halaman reservasi.
sealed class ResevUiState {
    // Subclass Success: Menunjukkan bahwa data berhasil diambil dan berisi daftar reservasi.
    data class Success(val reservasi: List<Reservasi>) : ResevUiState()

    // Subclass Error: Menunjukkan bahwa terjadi kesalahan saat mengambil data.
    object Error : ResevUiState()

    // Subclass Loading: Menunjukkan bahwa aplikasi sedang dalam proses memuat data.
    object Loading : ResevUiState()
}

// ViewModel untuk halaman reservasi yang bertanggung jawab mengelola data dan state UI.
class ReservasiViewModel(private val rsv: ResevRepository) : ViewModel() {

    // State UI untuk halaman reservasi. Default-nya adalah Loading.
    var rsvUiState: ResevUiState by mutableStateOf(ResevUiState.Loading)
        private set // Hanya dapat diubah dari dalam ViewModel.

    // Fungsi init akan dipanggil saat ViewModel dibuat. Memuat data reservasi saat pertama kali diinisialisasi.
    init {
        getRes()
    }

    // Fungsi untuk mengambil data reservasi dari repository.
    fun getRes() {
        viewModelScope.launch {
            rsvUiState = ResevUiState.Loading // Set state ke Loading sebelum memulai proses.
            rsvUiState = try {
                // Mengambil data reservasi dari repository dan mengubah state ke Success.
                ResevUiState.Success(rsv.getResev())
            } catch (e: IOException) {
                // Jika terjadi IOException (misalnya, masalah jaringan), set state ke Error.
                ResevUiState.Error
            } catch (e: HttpException) {
                // Jika terjadi HttpException (misalnya, respons API gagal), set state ke Error.
                ResevUiState.Error
            }
        }
    }

    // Fungsi untuk menghapus reservasi berdasarkan ID.
    fun deleteRes(id_reservasi: String) {
        viewModelScope.launch {
            try {
                // Menghapus reservasi dari repository.
                rsv.deleteResev(id_reservasi)
                // Memuat ulang data setelah penghapusan berhasil.
                getRes()
            } catch (e: IOException) {
                // Jika terjadi IOException, set state ke Error.
                ResevUiState.Error
            } catch (e: HttpException) {
                // Jika terjadi HttpException, set state ke Error.
                ResevUiState.Error
            }
        }
    }
}