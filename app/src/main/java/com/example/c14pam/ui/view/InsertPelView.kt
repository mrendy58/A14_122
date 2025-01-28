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
import com.example.c14pam.ui.viewmodel.InsertPelUiEvent
import com.example.c14pam.ui.viewmodel.InsertPelUiState
import com.example.c14pam.ui.viewmodel.InsertPelViewModel
import com.example.c14pam.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiEntryPelanggan : DestinasiNavigasi {
    override val route = "EntriPelanggan"
    override val titleRes = "Entry Pelanggan"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPelScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPelViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
                        text = "Tambah Pelanggan",
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
        EntryBodyPel(
            insertPelUiState = viewModel.insertPelUiState,
            onPelValueChange = { event ->
                viewModel.updateInsertPelState(event)
            },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPelanggan()
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
fun EntryBodyPel(
    insertPelUiState: InsertPelUiState,
    onPelValueChange: (InsertPelUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Form input
        FormInputPel(
            insertPelUiEvent = insertPelUiState.insertPelUiEvent,
            onValueChange = onPelValueChange,
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
fun FormInputPel(
    insertPelUiEvent: InsertPelUiEvent,
    onValueChange: (InsertPelUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Input ID Pelanggan
        OutlinedTextField(
            value = insertPelUiEvent.id_pelanggan,
            onValueChange = { onValueChange(insertPelUiEvent.copy(id_pelanggan = it)) },
            label = { Text("ID Pelanggan") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Input Nama Pelanggan
        OutlinedTextField(
            value = insertPelUiEvent.nama_pelanggan,
            onValueChange = { onValueChange(insertPelUiEvent.copy(nama_pelanggan = it)) },
            label = { Text("Nama Pelanggan") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Input No. HP
        OutlinedTextField(
            value = insertPelUiEvent.no_hp,
            onValueChange = { onValueChange(insertPelUiEvent.copy(no_hp = it)) },
            label = { Text("No. HP") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
    }
}