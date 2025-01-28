package com.example.c14pam.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c14pam.ui.navigation.DestinasiNavigasi
import com.example.c14pam.ui.viewmodel.InsertPelUiEvent
import com.example.c14pam.ui.viewmodel.InsertPelUiState
import com.example.c14pam.ui.viewmodel.PenyediaViewModel
import com.example.c14pam.ui.viewmodel.UpdatePelViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiPelUpdate : DestinasiNavigasi {
    override val route = "update_pelanggan"
    override val titleRes = "Update Pelanggan"
    const val ID_PELANGGAN = "id_pelanggan"
    val routesWithArg = "$route/{$ID_PELANGGAN}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePelScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdatePelViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            // AppBar dengan warna biru
            TopAppBar(
                title = {
                    Text(
                        text = DestinasiPelUpdate.titleRes,
                        color = Color.White // Warna teks putih
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
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
    ) { padding ->
        UpdateBodyPel(
            modifier = Modifier.padding(padding),
            insertPelUiState = viewModel.updatePelUiState,
            onPelValueChange = viewModel::updateInsertPelState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePelanggan()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}

@Composable
fun UpdateBodyPel(
    insertPelUiState: InsertPelUiState,
    onPelValueChange: (InsertPelUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Form input
        FormUpdatePel(
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
fun FormUpdatePel(
    insertPelUiEvent: InsertPelUiEvent,
    onValueChange: (InsertPelUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Input ID Pelanggan (read-only, karena ID tidak boleh diubah)
        OutlinedTextField(
            value = insertPelUiEvent.id_pelanggan,
            onValueChange = { }, // ID tidak boleh diubah
            label = { Text("ID Pelanggan") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = false // Nonaktifkan input
        )

        // Input Nama Pelanggan
        OutlinedTextField(
            value = insertPelUiEvent.nama_pelanggan,
            onValueChange = { onValueChange(insertPelUiEvent.copy(nama_pelanggan = it)) },
            label = { Text("Nama Pelanggan") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Input Nomor HP
        OutlinedTextField(
            value = insertPelUiEvent.no_hp,
            onValueChange = { onValueChange(insertPelUiEvent.copy(no_hp = it)) },
            label = { Text("Nomor HP") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true
        )
    }
}