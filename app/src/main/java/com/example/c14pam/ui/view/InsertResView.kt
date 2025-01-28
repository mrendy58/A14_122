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
import com.example.c14pam.ui.viewmodel.InsertResUiEvent
import com.example.c14pam.ui.viewmodel.InsertResUiState
import com.example.c14pam.ui.viewmodel.InsertResViewModel
import com.example.c14pam.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch


object DestinasiEntryResev : DestinasiNavigasi {

    override val route = "EntriResev"

    override val titleRes = "Entry Reservasi"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryResScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertResViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
                        text = "Tambah Villa",
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
        EntryBodyRes(
            insertResUiState = viewModel.insertResUiState,
            onReSValueChange = { event ->
                viewModel.updateInsertResState(event)
            },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertResev()
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
fun EntryBodyRes(
    insertResUiState: InsertResUiState,
    onReSValueChange: (InsertResUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Form input
        FormInputRes(
            insertResUiEvent = insertResUiState.insertResUiEvent,
            onValueChange = onReSValueChange,
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
    insertResUiEvent: InsertResUiEvent,
    onValueChange: (InsertResUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Input ID Reservasi
        OutlinedTextField(
            value = insertResUiEvent.id_reservasi,
            onValueChange = { onValueChange(insertResUiEvent.copy(id_reservasi = it)) },
            label = { Text("ID Reservasi") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Input Id Villa
        OutlinedTextField(
            value = insertResUiEvent.id_villa,
            onValueChange = { onValueChange(insertResUiEvent.copy(id_villa = it)) },
            label = { Text("ID Villa") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Input Id Pelanggan
        OutlinedTextField(
            value = insertResUiEvent.id_pelanggan,
            onValueChange = { onValueChange(insertResUiEvent.copy(id_pelanggan = it)) },
            label = { Text("ID Pelanggan") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Input check_in
        OutlinedTextField(
            value = insertResUiEvent.check_in,
            onValueChange = { onValueChange(insertResUiEvent.copy(check_in = it)) },
            label = { Text("check_in") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Input check_out
        OutlinedTextField(
            value = insertResUiEvent.check_out,
            onValueChange = { onValueChange(insertResUiEvent.copy(check_out = it)) },
            label = { Text("check_out") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Input jumlah Kamar
        OutlinedTextField(
            value = insertResUiEvent.jumlah_kamar,
            onValueChange = { onValueChange(insertResUiEvent.copy(jumlah_kamar = it)) },
            label = { Text("jumlah_kamar") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

    }
}