package com.example.c14pam.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.c14pam.model.Villa
import com.example.c14pam.ui.navigation.DestinasiNavigasi
import com.example.c14pam.ui.viewmodel.DetailVilUiState
import com.example.c14pam.ui.viewmodel.toVilla


object DestinasiVilDetail : DestinasiNavigasi {
    override val route = "detail"
    const val ID_VILLA = "id_villa"
    override val titleRes = "Detail Villa"
    val routeWithArg = "$route/{$ID_VILLA}"
}

@Composable
fun BodyDetailVla(
    detailVilUiState: DetailVilUiState,
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
        modifier = modifier.fillMaxWidth().padding(top = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White, // Warna latar belakang kartu putih
            contentColor = Color.Black // Warna teks hitam
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            ComponentDetailVla(judul = "Id Villa", isi = villa.id_villa, titleColor = Color(0xFF2196F3)) // Warna biru
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailVla(judul = "Nama Villa", isi = villa.nama_villa, titleColor = Color(0xFF2196F3)) // Warna biru
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailVla(judul = "Alamat", isi = villa.alamat, titleColor = Color(0xFF2196F3)) // Warna biru
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailVla(judul = "Kamar Tersedia", isi = villa.kamar_tersedia, titleColor = Color(0xFF2196F3)) // Warna biru
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun ComponentDetailVla(
    modifier: Modifier = Modifier,
    judul: String,
    isi: String,
    titleColor: Color = Color.Gray // Default warna abu-abu untuk judul
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = titleColor // Menggunakan warna judul yang dikirim
        )
        Text(
            text = isi,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black // Tulisan isi berwarna hitam
        )
    }
}