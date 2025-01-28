package com.example.c14pam.repository

import com.example.c14pam.model.Review
import com.example.c14pam.service.ReviewService
import java.io.IOException

// Interface ReviewRepository yang mendefinisikan operasi CRUD untuk entitas Review
interface ReviewRepository {
    suspend fun getReview(): List<Review> // Mendapatkan daftar semua review
    suspend fun insertReview(review: Review) // Menambahkan review baru
    suspend fun updateReview(id_review: String, review: Review) // Memperbarui data review berdasarkan ID
    suspend fun deleteReview(id_review: String) // Menghapus review berdasarkan ID
    suspend fun getReviewById(id_review: String): Review // Mendapatkan review berdasarkan ID
}

// Implementasi ReviewRepository menggunakan jaringan (API)
class NetworkReviewRepository(
    private val reviewService: ReviewService // Dependensi service untuk API review
) : ReviewRepository {

    // Implementasi metode untuk menambahkan review melalui API
    override suspend fun insertReview(review: Review) {
        reviewService.insertReview(review)
    }

    // Implementasi metode untuk memperbarui review melalui API
    override suspend fun updateReview(id_review: String, review: Review) {
        reviewService.updateReview(id_review, review)
    }

    // Implementasi metode untuk menghapus review melalui API
    override suspend fun deleteReview(id_review: String) {
        try {
            val response = reviewService.deleteReview(id_review) // Memanggil API untuk menghapus review
            if (!response.isSuccessful) { // Jika respons gagal
                throw IOException("Failed to delete Review. HTTP Status code: ${response.code()}") // Lempar exception
            } else {
                response.message() // Mendapatkan pesan sukses
                println(response.message()) // Mencetak pesan ke log
            }
        } catch (e: Exception) {
            throw e // Lempar exception jika terjadi error
        }
    }

    // Implementasi metode untuk mendapatkan daftar review melalui API
    override suspend fun getReview(): List<Review> = reviewService.getReview()

    // Implementasi metode untuk mendapatkan review berdasarkan ID melalui API
    override suspend fun getReviewById(id_review: String): Review {
        return reviewService.getReviewById(id_review)
    }
}