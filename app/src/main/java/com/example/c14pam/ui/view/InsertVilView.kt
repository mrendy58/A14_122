package com.example.c14pam.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.c14pam.ui.navigation.DestinasiNavigasi
import com.example.c14pam.ui.viewmodel.InsertVilUiEvent
import com.example.c14pam.ui.viewmodel.InsertVilUiState


object DestinasiEntryVilla : DestinasiNavigasi {

    override val route = "item_entry"

    override val titleRes = "Entry Villa"
}


@Composable
fun EntryBody(
    insertVilUiState: InsertVilUiState,
    onSiswaValueChange: (InsertVilUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Form input
        FormInput(
            insertVilUiEvent = insertVilUiState.insertVilUiEvent,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        // Tombol Simpan dengan warna biru
        Button(
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3), // Warna biru
                contentColor = Color.White // Warna teks putih
            )
        ) {
            Text("Simpan")
        }
    }
}

@Composable
fun FormInput(
    insertVilUiEvent: InsertVilUiEvent,
    onValueChange: (InsertVilUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Input ID Villa
        OutlinedTextField(
            value = insertVilUiEvent.id_villa,
            onValueChange = { onValueChange(insertVilUiEvent.copy(id_villa = it)) },
            label = { Text("ID Villa") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Input Nama Villa
        OutlinedTextField(
            value = insertVilUiEvent.nama_villa,
            onValueChange = { onValueChange(insertVilUiEvent.copy(nama_villa = it)) },
            label = { Text("Nama Villa") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Input Alamat
        OutlinedTextField(
            value = insertVilUiEvent.alamat,
            onValueChange = { onValueChange(insertVilUiEvent.copy(alamat = it)) },
            label = { Text("Alamat") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Input Kamar Tersedia
        OutlinedTextField(
            value = insertVilUiEvent.kamar_tersedia,
            onValueChange = { onValueChange(insertVilUiEvent.copy(kamar_tersedia = it)) },
            label = { Text("Kamar Tersedia") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )
    }
}