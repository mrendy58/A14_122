package com.example.c14pam


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.c14pam.ui.navigation.PengelolaHalaman

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RsvVillaApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    PengelolaHalaman(navController = navController)
        }
