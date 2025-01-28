package com.example.c14pam.service

import com.example.c14pam.model.Review
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ReviewService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacareview.php")
    suspend fun getReview(): List<Review>

    @GET("baca1review.php/{id_review}")
    suspend fun getReviewById(@Query("id_review") id_review: String): Review

    @POST("insertreview.php")
    suspend fun insertReview(@Body review: Review)

    @PUT("editreview.php/{id_review}")
    suspend fun updateReview(@Query("id_review") id_review: String, @Body review: Review)

    @DELETE("deletereview.php/{id_review}")
    suspend fun deleteReview(@Query("id_review") id_review: String): Response<Void>
}