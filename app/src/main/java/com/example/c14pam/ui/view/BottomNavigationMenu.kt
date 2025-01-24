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
import androidx.compose.ui.graphics.Color


@Composable
fun BottomNavigationMenu() {
    NavigationBar(
        containerColor = Color(0xFF2196F3) // Warna biru untuk navigation bar
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = Color.White // Warna ikon putih
                )
            },
            label = {
                Text(
                    text = "Home",
                    color = Color.White // Warna teks putih
                )
            },
            selected = true,
            onClick = { /* Navigate to Home */ }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Reservasi",
                    tint = Color.White // Warna ikon putih
                )
            },
            label = {
                Text(
                    text = "Reservasi",
                    color = Color.White // Warna teks putih
                )
            },
            selected = false,
            onClick = { /* Navigate to Reservasi */ }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Pelanggan",
                    tint = Color.White // Warna ikon putih
                )
            },
            label = {
                Text(
                    text = "Pelanggan",
                    color = Color.White // Warna teks putih
                )
            },
            selected = false,
            onClick = { /* Navigate to Pelanggan */ }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Review",
                    tint = Color.White // Warna ikon putih
                )
            },
            label = {
                Text(
                    text = "Review",
                    color = Color.White // Warna teks putih
                )
            },
            selected = false,
            onClick = { /* Navigate to Review */ }
        )
    }
}