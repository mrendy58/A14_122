package com.example.c14pam.service

import com.example.c14pam.model.Reservasi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ResevService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacareservasi.php")
    suspend fun getResev(): List<Reservasi>

    @GET("baca1reservasi.php/{id_reservasi}")
    suspend fun getResevById(@Query("id_reservasi") id_reservasi: String): Reservasi

    @POST("insertreservasi.php")
    suspend fun insertResev(@Body reservasi: Reservasi)

    @PUT("editreservasi.php/{id_reservasi}")
    suspend fun updateResev(@Query("id_reservasi") id_reservasi: String, @Body reservasi: Reservasi)

    @DELETE("deletereservasi.php/{id_reservasi}")
    suspend fun deleteResev(@Query("id_reservasi") id_reservasi: String): Response<Void>
}