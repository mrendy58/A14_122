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
import com.example.c14pam.ui.viewmodel.InsertVilUiEvent
import com.example.c14pam.ui.viewmodel.InsertVilUiState
import com.example.c14pam.ui.viewmodel.PenyediaViewModel
import com.example.c14pam.ui.viewmodel.UpdateVilViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object DestinasiVilUpdate : DestinasiNavigasi {
    override val route = "update"
    override val titleRes = "Update Villa"
    const val ID_VILLA = "id_villa"
    val routesWithArg = "$route/{$ID_VILLA}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateVilScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdateVilViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
                        text = DestinasiVilUpdate.titleRes,
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
        UpdateBody(
            modifier = Modifier.padding(padding),
            insertVilUiState = viewModel.updateVilUiState,
            onVillaValueChange = viewModel::updateInsertVilState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateVil()
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
fun UpdateBody(
    insertVilUiState: InsertVilUiState,
    onVillaValueChange: (InsertVilUiEvent) -> Unit,
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
        FormUpdate(
            insertVilUiEvent = insertVilUiState.insertVilUiEvent,
            onValueChange = onVillaValueChange,
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
fun FormUpdate(
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