package com.example.c14pam.ui.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.c14pam.R
import com.example.c14pam.model.Villa
import com.example.c14pam.ui.navigation.DestinasiNavigasi


// Objek untuk mendefinisikan rute dan judul layar home
object DestinasiHome : DestinasiNavigasi {
    override val route = "home" // Rute navigasi layar home
    override val titleRes = "Villa Untukmu"
}

@Composable
fun VillaCard(
    villa: Villa, // Data mahasiswa
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