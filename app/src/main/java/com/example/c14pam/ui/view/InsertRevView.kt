package com.example.c14pam.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c14pam.ui.navigation.DestinasiNavigasi
import com.example.c14pam.ui.viewmodel.InsertRevUiEvent
import com.example.c14pam.ui.viewmodel.InsertRevUiState
import com.example.c14pam.ui.viewmodel.InsertRevViewModel
import com.example.c14pam.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch


object DestinasiEntryRev : DestinasiNavigasi {

    override val route = "EntriReview"

    override val titleRes = "Entry Review"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryRevScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertRevViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier,
        topBar = {
            // AppBar dengan warna biru
            TopAppBar(
                title = {
                    Text(
                        text = "Tambah Review",
                        color = Color.White // Warna teks putih
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White // Warna ikon putih
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2196F3) // Warna biru
                )
            )
        }
    ) { innerPadding ->
        EntryBodyRev(
            insertRevUiState = viewModel.insertRevUiState,
            onRevValueChange = { event ->
                viewModel.updateInsertRevState(event)
            },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertReview()
                    navigateBack() // Kembali ke layar sebelumnya setelah menyimpan
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
fun EntryBodyRev(
    insertRevUiState: InsertRevUiState,
    onRevValueChange: (InsertRevUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Form input
        FormInputRes(
            insertRevUiEvent = insertRevUiState.insertRevUiEvent,
            onValueChange = onRevValueChange,
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
fun FormInputRes(
    insertRevUiEvent: InsertRevUiEvent,
    onValueChange: (InsertRevUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Input ID Review
        OutlinedTextField(
            value = insertRevUiEvent.id_review,
            onValueChange = { onValueChange(insertRevUiEvent.copy(id_review = it)) },
            label = { Text("ID Review") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Input Id Reservasi
        OutlinedTextField(
            value = insertRevUiEvent.id_reservasi,
            onValueChange = { onValueChange(insertRevUiEvent.copy(id_reservasi = it)) },
            label = { Text("ID Reservasi") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Input Nilai
        OutlinedTextField(
            value = insertRevUiEvent.nilai,
            onValueChange = { onValueChange(insertRevUiEvent.copy(nilai = it)) },
            label = { Text("ID Pelanggan") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Input Komentar
        OutlinedTextField(
            value = insertRevUiEvent.komentar,
            onValueChange = { onValueChange(insertRevUiEvent.copy(komentar = it)) },
            label = { Text("Komentar") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )


    }
}