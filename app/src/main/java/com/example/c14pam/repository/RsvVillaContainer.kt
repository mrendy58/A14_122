package com.example.c14pam.repository

import com.example.c14pam.service.ResevService
import com.example.c14pam.service.VillaService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

// Interface AppContainer digunakan untuk menyediakan dependensi yang diperlukan oleh aplikasi.
// Ini adalah bagian dari pola Dependency Injection (DI) untuk memisahkan pembuatan objek dari penggunaannya.
interface AppContainer {
    val villaRepository: VillaRepository // Properti untuk menyediakan instance VillaRepository
    val resevRepository: ResevRepository // Properti untuk menyediakan instance ResevRepository
}

// Implementasi dari AppContainer yang menyediakan instance VillaRepository.
class RsvVillaContainer : AppContainer {
    // Base URL untuk API. Dalam hal ini, menggunakan emulator Android (10.0.2.2 adalah localhost).
    private val baseurl = "http://10.0.2.2:82/Booking/"

    // Konfigurasi JSON untuk parsing data dari API.
    // ignoreUnknownKeys = true memungkinkan untuk mengabaikan field yang tidak dikenal dalam JSON.
    private val json = Json { ignoreUnknownKeys = true }

    // Membuat instance Retrofit untuk melakukan HTTP request ke API.
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseurl) // Mengatur base URL untuk API.
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType())) // Menggunakan Kotlinx.serialization sebagai converter.
        .build()

    // Lazy initialization untuk VillaService.
    // VillaService adalah interface yang mendefinisikan endpoint API.
    private val villaService: VillaService by lazy {
        retrofit.create(VillaService::class.java) // Membuat instance VillaService menggunakan Retrofit.
    }
    private val resevService: ResevService by lazy {
        retrofit.create(ResevService::class.java) // Membuat instance ResevService menggunakan Retrofit.
    }

    // Lazy initialization untuk VillaRepository.
    // NetworkVillaRepository adalah implementasi dari VillaRepository yang menggunakan VillaService.
    override val villaRepository: VillaRepository by lazy {
        NetworkVillaRepository(villaService) // Membuat instance NetworkVillaRepository dengan VillaService.
    }
    override val resevRepository: ResevRepository by lazy {
        NetworkResevRepository(resevService) // Membuat instance NetworkVillaRepository dengan VillaService.
    }
}