package com.example.c14pam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.c14pam.ui.view.DestinasiEntryVilla
import com.example.c14pam.ui.view.DestinasiHome
import com.example.c14pam.ui.view.DestinasiReservasi
import com.example.c14pam.ui.view.DestinasiVilDetail
import com.example.c14pam.ui.view.DestinasiVilUpdate
import com.example.c14pam.ui.view.DetailVilScreen
import com.example.c14pam.ui.view.EntryVilScreen
import com.example.c14pam.ui.view.HomeScreen
import com.example.c14pam.ui.view.ResevScreen
import com.example.c14pam.ui.view.UpdateVilScreen

@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route,
        modifier = Modifier,
    ) {
        // Halaman Home
        composable(DestinasiHome.route) {
            HomeScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiEntryVilla.route)
                },
                onDetailClick = { id_villa ->
                    navController.navigate("detail/$id_villa")
                }
            )
        }

        // Halaman EntryVil
        composable(DestinasiEntryVilla.route) {
            EntryVilScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Halaman Detail
        composable(
            DestinasiVilDetail.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiVilDetail.ID_VILLA) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val idVilla = backStackEntry.arguments?.getString(DestinasiVilDetail.ID_VILLA)
            idVilla?.let {
                DetailVilScreen(
                    navigateBack = {
                        navController.navigate(DestinasiHome.route) {
                            popUpTo(DestinasiHome.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToEdit = { idVilla ->
                        navController.navigate("${DestinasiVilUpdate.route}/$idVilla")
                    },
                    navigateToReservasi = { idResev ->
                        navController.navigate("${DestinasiReservasi.route}/$idResev")
                    }
                )
            }
        }

        // Halaman Update
        composable(
            DestinasiVilUpdate.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiVilUpdate.ID_VILLA) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val idVilla = backStackEntry.arguments?.getString(DestinasiVilUpdate.ID_VILLA)
            idVilla?.let {
                UpdateVilScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

        // Halaman Reservasi
        composable(
            DestinasiReservasi.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiReservasi.ID_RESERVASI) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val idReservasi = backStackEntry.arguments?.getString(DestinasiReservasi.ID_RESERVASI)
            idReservasi?.let {
                ResevScreen(
                    navigateToEntryReservasi = {
                        // Navigasi ke halaman tambah reservasi (jika diperlukan)
                    },
                    onDetailResClick = { idReservasiDetail ->
                        // Navigasi ke detail reservasi (jika diperlukan)
                    }
                )
            }
        }
    }
}