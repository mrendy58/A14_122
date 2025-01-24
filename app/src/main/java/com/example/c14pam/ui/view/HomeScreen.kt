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
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.example.c14pam.model.Villa
import com.example.c14pam.ui.navigation.DestinasiNavigasi
import com.example.c14pam.ui.viewmodel.HomeUiState
import com.example.c14pam.ui.viewmodel.HomeViewModel
import com.example.c14pam.ui.viewmodel.PenyediaViewModel


// Objek untuk mendefinisikan rute dan judul layar home
object DestinasiHome : DestinasiNavigasi {
    override val route = "home" // Rute navigasi layar home
    override val titleRes = "Villa Untukmu"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
                                    text = "Cari villa...",
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
                                .padding(start = 15.dp,end = 12.dp)
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
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp),
                contentColor = Color.White,
                containerColor = Color(0xFF2196F3) // Warna biru
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Villa")
            }
        },
        bottomBar = {
            BottomNavigationMenu()
        }
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.vlaUiState,
            retryAction = { viewModel.getVla() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = { villa ->
                viewModel.deleteVla(villa.id_villa)
            }
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: HomeUiState, // Status data villa
    retryAction: () -> Unit, // Aksi untuk memuat ulang
    modifier: Modifier = Modifier,
    onDeleteClick: (Villa) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize()) // Menampilkan loading
        is HomeUiState.Success -> {
            if (homeUiState.villa.isEmpty()) {
                // Tampilkan pesan jika data kosong
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Villa")
                }
            } else {
                // Tampilkan daftar mahasiswa
                VillaLayout(
                    villa = homeUiState.villa,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_villa) }, // Mengarahkan ke detail
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is HomeUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize()) // Tampilkan pesan error
    }
}

@Composable
fun OnLoading(
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
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
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
fun VillaLayout(
    villa: List<Villa>,
    modifier: Modifier = Modifier,
    onDetailClick: (Villa) -> Unit,
    onDeleteClick: (Villa) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(villa) { villa ->
            VillaCard(
                villa = villa,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(villa) }, // Fungsi klik untuk detail
                onDeleteClick = { onDeleteClick(villa) }
            )
        }
    }
}

@Composable
fun VillaCard(
    villa: Villa, // Data villa
    modifier: Modifier = Modifier,
    onDeleteClick: (Villa) -> Unit = {}
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
            // Gambar villa
            Image(
                painter = painterResource(id = R.drawable.villaimg),
                contentDescription = "Villa Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.medium)
            )

            Spacer(Modifier.height(16.dp)) // Jarak antar elemen

            // Informasi Villa
            Text(
                text = villa.nama_villa,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = villa.alamat,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Kamar Tersedia: ${villa.kamar_tersedia}",
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
                onDeleteClick(villa)
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