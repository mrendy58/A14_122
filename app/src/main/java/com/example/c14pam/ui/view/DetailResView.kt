package com.example.c14pam.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import com.example.c14pam.model.Reservasi
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
import com.example.c14pam.ui.viewmodel.DetailResUiState
import com.example.c14pam.ui.viewmodel.DetailResViewModel
import com.example.c14pam.ui.viewmodel.PenyediaViewModel
import com.example.c14pam.ui.viewmodel.toResev

object DestinasiDetailRes: DestinasiNavigasi {
    override val route = "detail_Resev"
    const val ID_RESERVASI = "id_reservasi"
    override val titleRes = "Detail Reservasi"
    val routeWithArg = "$route/{$ID_RESERVASI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailResScreen(
    navigateBack: () -> Unit,
    navigateToEditRes: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToReservasi: () -> Unit,
    navigateToPelanggan: () -> Unit,
    navigateToReview: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailResViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val currentRoute = DestinasiDetailRes.route // Ambil rute saat ini

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailRes.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToEditRes,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Reservasi"
                )
            }
        },
        bottomBar = {
            BottomNavigationMenu(
                onHomeClick = navigateToHome,
                onReservasiClick = navigateToReservasi,
                onPelangganClick = navigateToPelanggan,
                onReviewClick = navigateToReview
            )
        }
    ) { innerPadding ->
        BodyDetailRes(
            detailResUiState = viewModel.detailResUiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyDetailRes(
    detailResUiState: DetailResUiState,
    modifier: Modifier = Modifier
) {
    when {
        detailResUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        detailResUiState.isError -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailResUiState.errorMessage,
                    color = Color.Red
                )
            }
        }
        detailResUiState.isUiEventNotEmpty -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailRes(
                    reservasi = detailResUiState.detailResUiEvent.toResev(),
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun ItemDetailRes(
    modifier: Modifier = Modifier,
    reservasi: Reservasi
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
            ComponentDetailRes(judul = "Id Reservasi", isi = reservasi.id_reservasi, titleColor = Color(0xFF2196F3))
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailRes(judul = "Id Villa", isi = reservasi.id_villa, titleColor = Color(0xFF2196F3))
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailRes(judul = "Id Pelanggan ", isi = reservasi.id_pelanggan, titleColor = Color(0xFF2196F3))
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailRes(judul = "check In", isi = reservasi.check_in, titleColor = Color(0xFF2196F3))
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailRes(judul = "check Out", isi = reservasi.check_out, titleColor = Color(0xFF2196F3))
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailRes(judul = "Jumlah Kamar", isi = reservasi.jumlah_kamar, titleColor = Color(0xFF2196F3))
        }
    }
}

@Composable
fun ComponentDetailRes(
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