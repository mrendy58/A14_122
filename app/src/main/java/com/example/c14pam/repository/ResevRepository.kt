package com.example.c14pam.repository

import com.example.c14pam.model.Reservasi
import com.example.c14pam.service.ResevService
import java.io.IOException

// Interface ResevRepository yang mendefinisikan operasi CRUD untuk entitas Reservasi
interface ResevRepository {
    suspend fun getResev(): List<Reservasi> // Mendapatkan daftar semua reservasi
    suspend fun insertResev(reservasi: Reservasi) // Menambahkan reservasi baru
    suspend fun updateResev(id_reservasi: String, reservasi: Reservasi) // Memperbarui data reservasi berdasarkan ID
    suspend fun deleteResev(id_reservasi: String) // Menghapus reservasi berdasarkan ID
    suspend fun getResevById(id_reservasi: String): Reservasi // Mendapatkan reservasi berdasarkan ID
}

// Implementasi ResevRepository menggunakan jaringan (API)
class NetworkResevRepository(
    private val resevService: ResevService // Dependensi service untuk API reservasi
) : ResevRepository {

    // Implementasi metode untuk menambahkan reservasi melalui API
    override suspend fun insertResev(reservasi: Reservasi) {
        resevService.insertResev(reservasi)
    }

    // Implementasi metode untuk memperbarui reservasi melalui API
    override suspend fun updateResev(id_reservasi: String, reservasi: Reservasi) {
        resevService.updateResev(id_reservasi, reservasi)
    }

    // Implementasi metode untuk menghapus reservasi melalui API
    override suspend fun deleteResev(id_reservasi: String) {
        try {
            val response = resevService.deleteResev(id_reservasi) // Memanggil API untuk menghapus reservasi
            if (!response.isSuccessful) { // Jika respons gagal
                throw IOException("Failed to delete Reservasi. HTTP Status code: ${response.code()}") // Lempar exception
            } else {
                response.message() // Mendapatkan pesan sukses
                println(response.message()) // Mencetak pesan ke log
            }
        } catch (e: Exception) {
            throw e // Lempar exception jika terjadi error
        }
    }

    // Implementasi metode untuk mendapatkan daftar reservasi melalui API
    override suspend fun getResev(): List<Reservasi> = resevService.getResev()

    // Implementasi metode untuk mendapatkan reservasi berdasarkan ID melalui API
    override suspend fun getResevById(id_reservasi: String): Reservasi {
        return resevService.getResevById(id_reservasi)
    }
}