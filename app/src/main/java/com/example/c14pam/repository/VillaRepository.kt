package com.example.c14pam.repository

import com.example.c14pam.model.Villa
import com.example.c14pam.service.VillaService
import java.io.IOException

// Interface VillaRepository yang mendefinisikan operasi CRUD untuk entitas Villa
interface VillaRepository {
    suspend fun getVilla(): List<Villa> // Mendapatkan daftar semua villa
    suspend fun insertVilla(villa: Villa) // Menambahkan villa baru
    suspend fun updateVilla(id_villa: String, villa: Villa) // Memperbarui data villa berdasarkan ID
    suspend fun deleteVilla(id_villa: String) // Menghapus villa berdasarkan ID
    suspend fun getVillaById(id_villa: String): Villa // Mendapatkan villa berdasarkan ID
}

// Implementasi VillaRepository menggunakan jaringan (API)
class NetworkVillaRepository(
    private val villaService: VillaService // Dependensi service untuk API villa
) : VillaRepository {

    // Implementasi metode untuk menambahkan villa melalui API
    override suspend fun insertVilla(villa: Villa) {
        villaService.insertVilla(villa)
    }

    // Implementasi metode untuk memperbarui villa melalui API
    override suspend fun updateVilla(id_villa: String, villa: Villa) {
        villaService.updateVilla(id_villa, villa)
    }

    // Implementasi metode untuk menghapus villa melalui API
    override suspend fun deleteVilla(id_villa: String) {
        try {
            val response = villaService.deleteVilla(id_villa) // Memanggil API untuk menghapus villa
            if (!response.isSuccessful) { // Jika respons gagal
                throw IOException("Failed to delete Villa. HTTP Status code: ${response.code()}") // Lempar exception
            } else {
                response.message() // Mendapatkan pesan sukses
                println(response.message()) // Mencetak pesan ke log
            }
        } catch (e: Exception) {
            throw e // Lempar exception jika terjadi error
        }
    }

    // Implementasi metode untuk mendapatkan daftar villa melalui API
    override suspend fun getVilla(): List<Villa> = villaService.getVilla()

    // Implementasi metode untuk mendapatkan villa berdasarkan ID melalui API
    override suspend fun getVillaById(id_villa: String): Villa {
        return villaService.getVillaById(id_villa)
    }
}