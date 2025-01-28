package com.example.c14pam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.c14pam.RsvVillaApplication


object PenyediaViewModel{
    val Factory = viewModelFactory{
        // View Model Villa
        initializer { HomeViewModel(RsvVillaApplication().container.villaRepository) }
        initializer { InsertVilViewModel(RsvVillaApplication().container.villaRepository) }
        initializer { UpdateVilViewModel( createSavedStateHandle(),RsvVillaApplication().container.villaRepository) }
        initializer { DetailVilViewModel( createSavedStateHandle(),RsvVillaApplication().container.villaRepository) }

        // View Model Reservasi
        initializer { ReservasiViewModel(RsvVillaApplication().container.resevRepository) }
        initializer { InsertResViewModel(RsvVillaApplication().container.resevRepository) }
        initializer { UpdateResViewModel( createSavedStateHandle(),RsvVillaApplication().container.resevRepository) }
        initializer { DetailResViewModel( createSavedStateHandle(),RsvVillaApplication().container.resevRepository) }

        // View Model Pelanggan
        initializer { PelangganViewModel(RsvVillaApplication().container.pelangganRepository) }
        initializer { InsertPelViewModel(RsvVillaApplication().container.pelangganRepository) }
        initializer { UpdatePelViewModel( createSavedStateHandle(),RsvVillaApplication().container.pelangganRepository) }
        initializer { DetailPelViewModel( createSavedStateHandle(),RsvVillaApplication().container.pelangganRepository) }

        // View Model Reservasi
        initializer { ReviewViewModel(RsvVillaApplication().container.reviewRepository) }
        initializer { InsertRevViewModel(RsvVillaApplication().container.reviewRepository) }
        initializer { UpdateRevViewModel( createSavedStateHandle(),RsvVillaApplication().container.reviewRepository) }
        initializer { DetailRevViewModel( createSavedStateHandle(),RsvVillaApplication().container.reviewRepository) }

    }
}
    fun CreationExtras.RsvVillaApplication(): RsvVillaApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as RsvVillaApplication)
