package com.example.c14pam.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.c14pam.model.Review
import com.example.c14pam.repository.ReviewRepository
import com.example.c14pam.ui.view.DestinasiRevUpdate
import kotlinx.coroutines.launch

class UpdateRevViewModel (
    savedStateHandle: SavedStateHandle,
    private val rev: ReviewRepository
): ViewModel(){
    var updateRevUiState by mutableStateOf(InsertRevUiState())
        private set

    private val _idReview: String = checkNotNull(savedStateHandle[DestinasiRevUpdate.ID_REVIEW])

    init {
        viewModelScope.launch {
            updateRevUiState = rev.getReviewById(_idReview)
                .toUiStateRev()
        }
    }

    fun updateInsertRevState(insertRevUiEvent: InsertRevUiEvent){
        updateRevUiState = InsertRevUiState(insertRevUiEvent = insertRevUiEvent)
    }

    suspend fun updateRev(){
        viewModelScope.launch {
            try {
                rev.updateReview(_idReview, updateRevUiState.insertRevUiEvent.toRev())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}
fun Review.toUIStateRev(): InsertRevUiState = InsertRevUiState(
    insertRevUiEvent = this.toDetailRevUiEvent(),
)