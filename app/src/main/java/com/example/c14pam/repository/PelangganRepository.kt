package com.example.c14pam.repository

import com.example.c14pam.model.Pelanggan
import com.example.c14pam.service.PelangganService
import com.example.c14pam.service.ResevService
import java.io.IOException

// Interface ResevRepository yang mendefinisikan operasi CRUD untuk entitas Reservasi
interface PelangganRepository {
    suspend fun getPel(): List<Pelanggan> // Mendapatkan daftar semua reservasi
    suspend fun insertPel(pelanggan: Pelanggan) // Menambahkan reservasi baru
    suspend fun updatePel(id_pelanggan: String, pelanggan: Pelanggan) // Memperbarui data reservasi berdasarkan ID
    suspend fun deletePel(id_pelanggan: String) // Menghapus reservasi berdasarkan ID
    suspend fun getPelById(id_pelanggan: String): Pelanggan // Mendapatkan reservasi berdasarkan ID
}

// Implementasi ResevRepository menggunakan jaringan (API)
class NetworkPelRepository(
    private val pelangganService: PelangganService // Dependensi service untuk API reservasi
) : PelangganRepository {

    // Implementasi metode untuk menambahkan reservasi melalui API
    override suspend fun insertPel(pelanggan: Pelanggan) {
        pelangganService.insertPel(pelanggan)
    }

    // Implementasi metode untuk memperbarui reservasi melalui API
    override suspend fun updatePel(id_pelanggan: String, pelanggan: Pelanggan) {
        pelangganService.updatePel(id_pelanggan, pelanggan)
    }

    // Implementasi metode untuk menghapus reservasi melalui API
    override suspend fun deletePel(id_pelanggan: String) {
        try {
            val response = pelangganService.deletePel(id_pelanggan) // Memanggil API untuk menghapus reservasi
            if (!response.isSuccessful) { // Jika respons gagal
                throw IOException("Failed to delete Pelanggan. HTTP Status code: ${response.code()}") // Lempar exception
            } else {
                response.message() // Mendapatkan pesan sukses
                println(response.message()) // Mencetak pesan ke log
            }
        } catch (e: Exception) {
            throw e // Lempar exception jika terjadi error
        }
    }

    // Implementasi metode untuk mendapatkan daftar reservasi melalui API
    override suspend fun getPel(): List<Pelanggan> = pelangganService.getPel()

    // Implementasi metode untuk mendapatkan reservasi berdasarkan ID melalui API
    override suspend fun getPelById(id_pelanggan: String): Pelanggan {
        return pelangganService.getPelById(id_pelanggan)
    }
}