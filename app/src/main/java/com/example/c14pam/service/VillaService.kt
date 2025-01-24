package com.example.c14pam.service

import com.example.c14pam.model.Villa
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface VillaService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacavilla.php")
    suspend fun getVilla(): List<Villa>

    @GET("baca1villa.php/{id_villa}")
    suspend fun getVillaById(@Query("id_villa") id_villa: String): Villa

    @POST("insertvilla.php")
    suspend fun insertVilla(@Body villa: Villa)

    @PUT("editvilla.php/{id_villa}")
    suspend fun updateVilla(@Query("id_villa") id_villa: String, @Body villa: Villa)

    @DELETE("deletevilla.php/{id_villa}")
    suspend fun deleteVilla(@Query("id_villa") id_villa: String): Response<Void>
}