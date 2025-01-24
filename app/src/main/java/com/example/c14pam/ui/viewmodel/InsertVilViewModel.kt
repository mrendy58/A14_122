package com.example.c14pam.ui.viewmodel


import InsertVilUiEvent
import InsertVilUiState
import com.example.c14pam.model.Villa




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