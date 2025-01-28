package com.example.c14pam.ui.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavigationMenu(
    onHomeClick: () -> Unit,
    onReservasiClick: () -> Unit,
    onPelangganClick: () -> Unit,
    onReviewClick: () -> Unit,
) {
    // State untuk menyimpan item yang sedang dipilih
    val selectedItem = remember { mutableStateOf("Home") }

    // Daftar item navigation (Home, Reservasi, Pelanggan, dan Review)
    val items = listOf(
        NavigationItem("Home", Icons.Default.Home, onHomeClick),
        NavigationItem("Reservasi", Icons.Default.DateRange, onReservasiClick),
        NavigationItem("Pelanggan", Icons.Default.Person, onPelangganClick),
        NavigationItem("Review", Icons.Default.Star, onReviewClick)
    )

    NavigationBar(
        containerColor = Color(0xFF2196F3) // Warna biru untuk navigation bar
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = Color.White // Warna ikon putih
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = Color.White // Warna teks putih
                    )
                },
                selected = selectedItem.value == item.label,
                onClick = {
                    selectedItem.value = item.label // Update state
                    item.onClick() // Panggil fungsi onClick
                }
            )
        }
    }
}

// Data class untuk menyimpan informasi navigation item
data class NavigationItem(
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val onClick: () -> Unit
)