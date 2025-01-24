package com.example.c14pam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.c14pam.RsvVillaApplication


object PenyediaViewModel{
    val Factory = viewModelFactory{
        initializer { HomeViewModel(RsvVillaApplication().container.villaRepository) }
        initializer { InsertVilViewModel(RsvVillaApplication().container.villaRepository) }
        initializer { UpdateVilViewModel( createSavedStateHandle(),RsvVillaApplication().container.villaRepository) }
        initializer { DetailVilViewModel( createSavedStateHandle(),RsvVillaApplication().container.villaRepository) }

    }
}
    fun CreationExtras.RsvVillaApplication(): RsvVillaApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as RsvVillaApplication)
