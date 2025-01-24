package com.example.c14pam.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.c14pam.ui.navigation.DestinasiNavigasi


object DestinasiVilDetail : DestinasiNavigasi {
    override val route = "detail"
    const val ID_VILLA = "id_villa"
    override val titleRes = "Detail Villa"
    val routeWithArg = "$route/{$ID_VILLA}"
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