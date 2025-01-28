package com.example.c14pam.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.c14pam.ui.navigation.DestinasiNavigasi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.c14pam.R
import com.example.c14pam.model.Pelanggan
import com.example.c14pam.model.Review
import com.example.c14pam.ui.viewmodel.ReviewViewModel
import com.example.c14pam.ui.viewmodel.PelangganUiState
import com.example.c14pam.ui.viewmodel.PenyediaViewModel
import com.example.c14pam.ui.viewmodel.ReviewUiState

// Objek untuk mendefinisikan rute dan judul layar review
object DestinasiReview : DestinasiNavigasi {
    override val route = "review/{idReview}" // Rute dengan parameter idReview
    override val titleRes = "Review"
    const val ID_REVIEW = "idReview" // Key untuk parameter idReview
    val routeWithArg = "$route/{$ID_REVIEW}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    navigateToEntryReview: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToReservasi: () -> Unit,
    navigateToPelanggan: () -> Unit,
    navigateToReview: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailRevClick: (String) -> Unit = {},
    viewModel: ReviewViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var searchQuery by remember { mutableStateOf("") } // State untuk menyimpan query pencarian

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .background(Color.White), // Latar belakang layar putih
        topBar = {
            // AppBar dengan warna biru
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // TextField untuk pencarian
                        TextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = {
                                Text(
                                    text = "Cari Review...",
                                    color = Color.Gray
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = Color.Gray
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp) // Mengatur tinggi TextField
                                .padding(bottom = 1.dp, end = 8.dp) // Padding di sisi bawah dan kanan
                                .background(Color.White, MaterialTheme.shapes.small)
                                .clip(MaterialTheme.shapes.small)
                        )
                        // Icon notifikasi
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(start = 15.dp, end = 12.dp)
                                .size(28.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF2196F3) // Warna biru
                ),
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToEntryReview,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                contentColor = Color.White,
                containerColor = Color(0xFF2196F3) // Warna biru
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Review")
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
        ReviewStatus(
            reviewUiState = viewModel.revUiState,
            retryAction = { viewModel.getRev() },
            modifier = Modifier.padding(innerPadding),
            onDetailRevClick = onDetailRevClick,
            onDeleteRevClick = { review ->
                viewModel.deleteRev(review.id_review)
            }
        )
    }
}

@Composable
fun ReviewStatus(
    reviewUiState: ReviewUiState, // Status data Review
    retryAction: () -> Unit, // Aksi untuk memuat ulang
    modifier: Modifier = Modifier,
    onDeleteRevClick: (Review) -> Unit = {},
    onDetailRevClick: (String) -> Unit
) {
    when (reviewUiState) {
        is ReviewUiState.Loading -> OnLoadingRev(modifier = modifier.fillMaxSize()) // Menampilkan loading
        is ReviewUiState.Success -> {
            if (reviewUiState.review.isEmpty()) {
                // Tampilkan pesan jika data kosong
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Review")
                }
            } else {
                // Tampilkan daftar Review
                ReviewLayout(
                    review = reviewUiState.review,
                    modifier = modifier.fillMaxWidth(),
                    onDetailRevClick = { onDetailRevClick(it.id_review) }, // Mengarahkan ke detail
                    onDeleteRevClick = { onDeleteRevClick(it) }
                )
            }
        }
        is ReviewUiState.Error -> OnErrorRev(retryAction, modifier = modifier.fillMaxSize()) // Tampilkan pesan error
    }
}

@Composable
fun OnLoadingRev(
    modifier: Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(70.dp), // Ukuran eksplisit di sini
            painter = painterResource(id = R.drawable.loading),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun OnErrorRev(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
fun ReviewLayout(
    review: List<Review>,
    modifier: Modifier = Modifier,
    onDetailRevClick: (Review) -> Unit,
    onDeleteRevClick: (Review) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(review) { review ->
            ReviewCard(
                review = review,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailRevClick(review) }, // Fungsi klik untuk detail
                onDeleteRevClick = { onDeleteRevClick(review) }
            )
        }
    }
}

@Composable
fun ReviewCard(
    review: Review, // Data Review
    modifier: Modifier = Modifier,
    onDeleteRevClick: (Review) -> Unit = {}
) {
    // State untuk menampilkan dialog konfirmasi
    var deleteConfirmationRequired by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .shadow(8.dp, shape = MaterialTheme.shapes.medium), // Tambahkan bayangan
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Warna latar belakang kartu
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Informasi Review
            Text(
                text = review.id_reservasi,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = review.nilai,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = review.komentar,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )


            // Tombol hapus
            IconButton(
                onClick = { deleteConfirmationRequired = true },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }

    // Dialog konfirmasi hapus
    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDeleteRevClick(review)
            },
            onDeleteCancel = { deleteConfirmationRequired = false }
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { onDeleteCancel() },
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data ini?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}