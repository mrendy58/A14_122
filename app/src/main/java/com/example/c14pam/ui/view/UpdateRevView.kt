package com.example.c14pam.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c14pam.ui.navigation.DestinasiNavigasi
import com.example.c14pam.ui.viewmodel.InsertRevUiState
import com.example.c14pam.ui.viewmodel.InsertRevUiEvent
import com.example.c14pam.ui.viewmodel.PenyediaViewModel
import com.example.c14pam.ui.viewmodel.UpdateRevViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object DestinasiRevUpdate : DestinasiNavigasi {
    override val route = "update_Review"
    override val titleRes = "Update Review"
    const val ID_REVIEW = "id_review"
    val routesWithArg = "$route/{$ID_REVIEW}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateRevScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate: () -> Unit,
    viewModel: UpdateRevViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
        UpdateBodyRev(
            modifier = Modifier.padding(padding),
            insertRevUiState = viewModel.updateRevUiState,
            onRevValueChange = viewModel::updateInsertRevState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateRev()
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
fun UpdateBodyRev(
    insertRevUiState: InsertRevUiState,
    onRevValueChange: (InsertRevUiEvent) -> Unit,
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
        FormUpdateRev(
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
fun FormUpdateRev(
    insertRevUiEvent: InsertRevUiEvent,
    onValueChange: (InsertRevUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Input Id Review
        OutlinedTextField(
            value = insertRevUiEvent.id_review,
            onValueChange = { onValueChange(insertRevUiEvent.copy(id_review = it)) },
            label = { Text("ID Review") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        // Input ID Reservasi
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
            label = { Text("Nilai") },
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