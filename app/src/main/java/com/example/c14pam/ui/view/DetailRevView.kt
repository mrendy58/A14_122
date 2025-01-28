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
import com.example.c14pam.model.Review
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
import com.example.c14pam.ui.viewmodel.DetailRevUiState
import com.example.c14pam.ui.viewmodel.DetailRevViewModel
import com.example.c14pam.ui.viewmodel.PenyediaViewModel
import com.example.c14pam.ui.viewmodel.toRev

object DestinasiDetailRev: DestinasiNavigasi {
    override val route = "detail_Review"
    const val ID_REVIEW = "id_review"
    override val titleRes = "Detail Review"
    val routeWithArg = "$route/{$ID_REVIEW}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailRevScreen(
    navigateBack: () -> Unit,
    navigateToEditRev: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToReservasi: () -> Unit,
    navigateToPelanggan: () -> Unit,
    navigateToReview: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailRevViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val currentRoute = DestinasiDetailRev.route // Ambil rute saat ini

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopAppBar(
                title = DestinasiDetailRev.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToEditRev,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Review"
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
        BodyDetailRev(
            detailRevUiState = viewModel.detailRevUiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BodyDetailRev(
    detailRevUiState: DetailRevUiState,
    modifier: Modifier = Modifier
) {
    when {
        detailRevUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        detailRevUiState.isError -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailRevUiState.errorMessage,
                    color = Color.Red
                )
            }
        }
        detailRevUiState.isUiEventNotEmpty -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailRev(
                    review = detailRevUiState.detailRevUiEvent.toRev(),
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun ItemDetailRev(
    modifier: Modifier = Modifier,
    review: Review
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
            ComponentDetailRev(judul = "Id Review", isi = review.id_review, titleColor = Color(0xFF2196F3))
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailRev(judul = "d Reservasi", isi = review.id_reservasi, titleColor = Color(0xFF2196F3))
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailRev(judul = "Nilai ", isi = review.nilai, titleColor = Color(0xFF2196F3))
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailRev(judul = "Komentar", isi = review.komentar, titleColor = Color(0xFF2196F3))
        }
    }
}

@Composable
fun ComponentDetailRev(
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