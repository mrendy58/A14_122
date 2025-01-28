package com.example.c14pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c14pam.model.Reservasi
import com.example.c14pam.model.Review
import com.example.c14pam.repository.ReviewRepository
import com.example.c14pam.ui.view.DestinasiDetailRev
import kotlinx.coroutines.launch

class DetailRevViewModel(
    savedStateHandle: SavedStateHandle,
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    private val id_review: String = checkNotNull(savedStateHandle[DestinasiDetailRev.ID_REVIEW])

    var detailRevUiState: DetailRevUiState by mutableStateOf(DetailRevUiState())
        private set

    init {
        getReviewById()
    }

    private fun getReviewById() {
        viewModelScope.launch {
            detailRevUiState = DetailRevUiState(isLoading = true)
            try {
                val result = reviewRepository.getReviewById(id_review)
                detailRevUiState = DetailRevUiState(
                    detailRevUiEvent = result.toDetailRevUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailRevUiState = DetailRevUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
}

data class DetailRevUiState(
    val detailRevUiEvent: InsertRevUiEvent = InsertRevUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiEventEmpty: Boolean
        get() = detailRevUiEvent == InsertRevUiEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailRevUiEvent != InsertRevUiEvent()
}

fun Review.toDetailRevUiEvent(): InsertRevUiEvent{
    return InsertRevUiEvent(
        id_review = id_review,
        id_reservasi = id_reservasi,
        nilai = nilai,
        komentar = komentar
    )
}