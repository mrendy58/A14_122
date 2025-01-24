package com.example.c14pam.model

import kotlinx.serialization.Serializable

@Serializable
data class Villa(
    val id_villa: String,
    val nama_villa: String,
    val alamat: String,
    val kamar_tersedia: String
)

@Serializable
data class Reservasi(
    val id_reservasi: String,
    val id_villa: String,
    val id_pelanggan: String,
    val check_in: String,
    val check_out: String,
    val jumlah_kamar: String
)
