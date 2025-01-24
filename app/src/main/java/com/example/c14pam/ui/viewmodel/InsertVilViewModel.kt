package com.example.c14pam.ui.viewmodel


import com.example.c14pam.model.Villa


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