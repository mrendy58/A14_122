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
import com.example.c14pam.ui.viewmodel.InsertResUiEvent
import com.example.c14pam.ui.viewmodel.InsertResUiState
import com.example.c14pam.ui.viewmodel.InsertVilUiEvent
import com.example.c14pam.ui.viewmodel.InsertVilUiState
import com.example.c14pam.ui.viewmodel.PenyediaViewModel
import com.example.c14pam.ui.viewmodel.UpdateResViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object DestinasiResUpdate : DestinasiNavigasi {
    override val route = "update_Resev"
    override val titleRes = "Update Reservasi"
    const val ID_RESERVASI = "id_reservasi"
    val routesWithArg = "$route/{$ID_RESERVASI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateResScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdateResViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
        UpdateBodyRes(
            modifier = Modifier.padding(padding),
            insertResUiState = viewModel.updateResUiState,
            onResValueChange = viewModel::updateInsertResState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateRes()
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
fun UpdateBodyRes(
    insertResUiState: InsertResUiState,
    onResValueChange: (InsertResUiEvent) -> Unit,
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
        FormUpdateRes(
            insertResUiEvent = insertResUiState.insertResUiEvent,
            onValueChange = onResValueChange,
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
fun FormUpdateRes(
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
        // Input Id pelanggan
        OutlinedTextField(
            value = insertResUiEvent.id_pelanggan,
            onValueChange = { onValueChange(insertResUiEvent.copy(id_pelanggan = it)) },
            label = { Text("ID pelanggan") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Input Check in
        OutlinedTextField(
            value = insertResUiEvent.check_in,
            onValueChange = { onValueChange(insertResUiEvent.copy(check_in = it)) },
            label = { Text("Check In") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Input Check Out
        OutlinedTextField(
            value = insertResUiEvent.check_out,
            onValueChange = { onValueChange(insertResUiEvent.copy(check_out = it)) },
            label = { Text("Check Out") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Input Jumlah Kamar
        OutlinedTextField(
            value = insertResUiEvent.jumlah_kamar,
            onValueChange = { onValueChange(insertResUiEvent.copy(jumlah_kamar = it)) },
            label = { Text("Jumlah Kamar ") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )
    }
}