package com.example.c14pam.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c14pam.model.Villa
import com.example.c14pam.ui.navigation.DestinasiNavigasi
import com.example.c14pam.ui.viewmodel.DetailVilUiState
import com.example.c14pam.ui.viewmodel.DetailVilViewModel
import com.example.c14pam.ui.viewmodel.PenyediaViewModel
import com.example.c14pam.ui.viewmodel.toVilla


object DestinasiVilDetail : DestinasiNavigasi {
    override val route = "detail"
    const val ID_VILLA = "id_villa"
    override val titleRes = "Detail Villa"
    val routeWithArg = "$route/{$ID_VILLA}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailVilScreen(
    navigateBack: () -> Unit,
    navigateToEdit: (String) -> Unit,
    navigateToReservasi: () -> Unit, // Fungsi untuk navigasi ke halaman reservasi
    modifier: Modifier = Modifier,
    viewModel: DetailVilViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = DestinasiVilDetail.titleRes,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Kembali",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2196F3)
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navigateToEdit(viewModel.detailVilUiState.detailVilUiEvent.id_villa)
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                containerColor = Color(0xFF2196F3),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Villa"
                )
            }
        },
        bottomBar = {
            BottomNavigationMenu()
        }
    ) { innerPadding ->
        BodyDetailVla(
            detailVilUiState = viewModel.detailVilUiState,
            navigateToReservasi = navigateToReservasi, // Meneruskan fungsi navigasi ke BodyDetailVla
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyDetailVla(
    detailVilUiState: DetailVilUiState,
    navigateToReservasi: () -> Unit, // Fungsi untuk navigasi ke halaman reservasi
    modifier: Modifier = Modifier
) {
    when {
        detailVilUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        detailVilUiState.isError -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailVilUiState.errorMessage,
                    color = Color.Red
                )
            }
        }
        detailVilUiState.isUiEventNotEmpty -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailVla(
                    villa = detailVilUiState.detailVilUiEvent.toVilla(),
                    modifier = modifier
                )
                // Tombol Reservasi
                Button(
                    onClick = navigateToReservasi,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp), // Padding atas dikurangi
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2196F3), // Warna biru
                        contentColor = Color.White // Warna teks putih
                    )
                ) {
                    Text("Reservasi")
                }
            }
        }
    }
}

@Composable
fun ItemDetailVla(
    modifier: Modifier = Modifier,
    villa: Villa
) {
    Card(
        modifier = modifier.fillMaxWidth().padding(top = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFDCEEFA), // Warna biru muda transparan
            contentColor = Color.Black
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Menambahkan shadow
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            ComponentDetailVla(judul = "Id Villa", isi = villa.id_villa, titleColor = Color(0xFF2196F3))
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailVla(judul = "Nama Villa", isi = villa.nama_villa, titleColor = Color(0xFF2196F3))
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailVla(judul = "Alamat", isi = villa.alamat, titleColor = Color(0xFF2196F3))
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailVla(judul = "Kamar Tersedia", isi = villa.kamar_tersedia, titleColor = Color(0xFF2196F3))
        }
    }
}

@Composable
fun ComponentDetailVla(
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