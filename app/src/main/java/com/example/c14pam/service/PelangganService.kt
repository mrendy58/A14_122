package com.example.c14pam.service

import com.example.c14pam.model.Pelanggan
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PelangganService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacapelanggan.php")
    suspend fun getPel(): List<Pelanggan>

    @GET("baca1pelanggan.php/{id_pelanggan}")
    suspend fun getPelById(@Query("id_pelanggan") id_pelanggan: String): Pelanggan

    @POST("insertpelanggan.php")
    suspend fun insertPel(@Body pelanggan: Pelanggan)

    @PUT("editpelanggan.php/{id_pelanggan}")
    suspend fun updatePel(@Query("id_pelanggan") id_pelanggan: String, @Body pelanggan: Pelanggan)

    @DELETE("deletepelanggan.php/{id_pelanggan}")
    suspend fun deletePel(@Query("id_pelanggan") id_pelanggan: String): Response<Void>
}