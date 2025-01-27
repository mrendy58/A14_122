package com.example.c14pam.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import com.example.c14pam.model.Pelanggan
import androidx.compose.material3.Card
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c14pam.ui.customwidget.CustomTopAppBar
import com.example.c14pam.ui.navigation.DestinasiNavigasi
import com.example.c14pam.ui.viewmodel.DetailPelUiState
import com.example.c14pam.ui.viewmodel.DetailPelViewModel
import com.example.c14pam.ui.viewmodel.PenyediaViewModel
import com.example.c14pam.ui.viewmodel.toPelanggan

object DestinasiDetailPel : DestinasiNavigasi {
    override val route = "detail_Pelanggan"
    const val ID_PELANGGAN = "id_pelanggan"
    override val titleRes = "Detail Pelanggan"
    val routeWithArg = "$route/{$ID_PELANGGAN}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPelScreen(
    navigateBack: () -> Unit,
    navigateToEditPel: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailPelViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailPel.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToEditPel,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Pelanggan"
                )
            }
        }
    ) { innerPadding ->
        BodyDetailPel(
            detailPelUiState = viewModel.detailPelUiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyDetailPel(
    detailPelUiState: DetailPelUiState,
    modifier: Modifier = Modifier
) {
    when {
        detailPelUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        detailPelUiState.isError -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailPelUiState.errorMessage,
                    color = Color.Red
                )
            }
        }
        detailPelUiState.isUiEventNotEmpty -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailPel(
                    pelanggan = detailPelUiState.detailPelUiEvent.toPelanggan(),
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun ItemDetailPel(
    modifier: Modifier = Modifier,
    pelanggan: Pelanggan
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp), // Padding di luar card
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFDCEEFA), // Warna biru muda transparan
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Menambahkan shadow
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp) // Padding di dalam card
        ) {
            ComponentDetailPel(judul = "ID Pelanggan", isi = pelanggan.id_pelanggan, titleColor = Color(0xFF2196F3))
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailPel(judul = "Nama Pelanggan", isi = pelanggan.nama_pelanggan, titleColor = Color(0xFF2196F3))
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailPel(judul = "No. HP", isi = pelanggan.no_hp, titleColor = Color(0xFF2196F3))
        }
    }
}

@Composable
fun ComponentDetailPel(
    modifier: Modifier = Modifier,
    judul: String,
    isi: String,
    titleColor: Color = Color.Gray
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = judul,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = titleColor
        )
        Text(
            text = isi,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}