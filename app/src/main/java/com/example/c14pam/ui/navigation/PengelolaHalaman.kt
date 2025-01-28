package com.example.c14pam.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.c14pam.ui.view.DestinasiDetailPel
import com.example.c14pam.ui.view.DestinasiDetailRes
import com.example.c14pam.ui.view.DestinasiDetailRev
import com.example.c14pam.ui.view.DestinasiDetailRev.ID_REVIEW
import com.example.c14pam.ui.view.DestinasiEntryPelanggan
import com.example.c14pam.ui.view.DestinasiEntryResev
import com.example.c14pam.ui.view.DestinasiEntryRev
import com.example.c14pam.ui.view.DestinasiEntryVilla
import com.example.c14pam.ui.view.DestinasiHome
import com.example.c14pam.ui.view.DestinasiPelUpdate
import com.example.c14pam.ui.view.DestinasiPelanggan
import com.example.c14pam.ui.view.DestinasiResUpdate
import com.example.c14pam.ui.view.DestinasiReservasi
import com.example.c14pam.ui.view.DestinasiRevUpdate
import com.example.c14pam.ui.view.DestinasiReview
import com.example.c14pam.ui.view.DestinasiVilDetail
import com.example.c14pam.ui.view.DestinasiVilUpdate
import com.example.c14pam.ui.view.DetailPelScreen
import com.example.c14pam.ui.view.DetailResScreen
import com.example.c14pam.ui.view.DetailRevScreen
import com.example.c14pam.ui.view.DetailVilScreen
import com.example.c14pam.ui.view.EntryPelScreen
import com.example.c14pam.ui.view.EntryResScreen
import com.example.c14pam.ui.view.EntryRevScreen
import com.example.c14pam.ui.view.EntryVilScreen
import com.example.c14pam.ui.view.HomeScreen
import com.example.c14pam.ui.view.PelangganScreen
import com.example.c14pam.ui.view.ResevScreen
import com.example.c14pam.ui.view.ReviewScreen
import com.example.c14pam.ui.view.UpdatePelScreen
import com.example.c14pam.ui.view.UpdateResScreen
import com.example.c14pam.ui.view.UpdateRevScreen
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
                    navController.navigate("${DestinasiVilDetail.route}/$id_villa")
                }
            )
        }

        // Halaman EntryVil
        composable(DestinasiEntryVilla.route) {
            EntryVilScreen(
                navigateBack = {
                    navController.navigate(DestinasiHome.route) {
                        popUpTo(DestinasiHome.route) { inclusive = true }
                    }
                }
            )
        }

        // Halaman Detail Villa
        composable(
            DestinasiVilDetail.routeWithArg,
            arguments = listOf(navArgument(DestinasiVilDetail.ID_VILLA) { type = NavType.StringType })
        ) { backStackEntry ->
            val idVilla = backStackEntry.arguments?.getString(DestinasiVilDetail.ID_VILLA)
            idVilla?.let {
                DetailVilScreen(
                    navigateBack = {
                        navController.navigate(DestinasiHome.route) {
                            popUpTo(DestinasiHome.route) { inclusive = true }
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

        // Halaman Update Villa
        composable(
            DestinasiVilUpdate.routesWithArg,
            arguments = listOf(navArgument(DestinasiVilUpdate.ID_VILLA) { type = NavType.StringType })
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
            arguments = listOf(navArgument(DestinasiReservasi.ID_RESERVASI) { type = NavType.StringType })
        ) { backStackEntry ->
            val idReservasi = backStackEntry.arguments?.getString(DestinasiReservasi.ID_RESERVASI)
            idReservasi?.let {
                ResevScreen(
                    navigateToEntryReservasi = {
                        navController.navigate(DestinasiEntryResev.route)
                    },
                    onDetailResClick = { idReservasiDetail ->
                        navController.navigate("${DestinasiDetailRes.route}/$idReservasiDetail")
                    }
                )
            }
        }

        // Halaman Entry Reservasi
        composable(DestinasiEntryResev.route) {
            EntryResScreen(
                navigateBack = {
                    navController.navigate(DestinasiReservasi.route) {
                        popUpTo(DestinasiReservasi.route) { inclusive = true }
                    }
                }
            )
        }

        // Halaman Detail Reservasi
        composable(
            DestinasiDetailRes.routeWithArg,
            arguments = listOf(navArgument(DestinasiDetailRes.ID_RESERVASI) { type = NavType.StringType })
        ) { backStackEntry ->
            val idReservasi = backStackEntry.arguments?.getString(DestinasiDetailRes.ID_RESERVASI)
            idReservasi?.let {
                DetailResScreen(
                    navigateBack = {
                        navController.navigate(DestinasiReservasi.route) {
                            popUpTo(DestinasiReservasi.route) { inclusive = true }
                        }
                    },
                    navigateToEditRes = { idReservasi ->
                        navController.navigate("${DestinasiResUpdate.route}/$idReservasi")
                    }
                )
            }
        }

        // Halaman Update Reservasi
        composable(
            DestinasiResUpdate.routeWithArg,
            arguments = listOf(navArgument(DestinasiResUpdate.ID_RESERVASI) { type = NavType.StringType })
        ) { backStackEntry ->
            val idReservasi = backStackEntry.arguments?.getString(DestinasiResUpdate.ID_RESERVASI)
            idReservasi?.let {
                UpdateResScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

        // Halaman Pelanggan
        composable(DestinasiPelanggan.route) {
            PelangganScreen(
                navigateToEntryPelanggan = {
                    navController.navigate(DestinasiEntryPelanggan.route)
                },
                onDetailPelClick = { idPelanggan ->
                    navController.navigate("${DestinasiDetailPel.route}/$idPelanggan")
                }
            )
        }

        // Halaman Entry Pelanggan
        composable(DestinasiEntryPelanggan.route) {
            EntryPelScreen(
                navigateBack = {
                    navController.navigate(DestinasiPelanggan.route) {
                        popUpTo(DestinasiPelanggan.route) { inclusive = true }
                    }
                }
            )
        }

        // Halaman Detail Pelanggan
        composable(
            DestinasiDetailPel.routeWithArg,
            arguments = listOf(navArgument(DestinasiDetailPel.ID_PELANGGAN) { type = NavType.StringType })
        ) { backStackEntry ->
            val idPelanggan = backStackEntry.arguments?.getString(DestinasiDetailPel.ID_PELANGGAN)
            idPelanggan?.let {
                DetailPelScreen(
                    navigateBack = {
                        navController.navigate(DestinasiPelanggan.route) {
                            popUpTo(DestinasiPelanggan.route) { inclusive = true }
                        }
                    },
                    navigateToEditPel = { idPelanggan ->
                        navController.navigate("${DestinasiPelUpdate.route}/$idPelanggan")
                    }
                )
            }
        }

        // Halaman Update Pelanggan
        composable(
            DestinasiPelUpdate.routeWithArg,
            arguments = listOf(navArgument(DestinasiPelUpdate.ID_PELANGGAN) { type = NavType.StringType })
        ) { backStackEntry ->
            val idPelanggan = backStackEntry.arguments?.getString(DestinasiPelUpdate.ID_PELANGGAN)
            idPelanggan?.let {
                UpdatePelScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }

        // Halaman Review
        composable(DestinasiReview.route) {
            ReviewScreen(
                navigateToEntryReview = {
                    navController.navigate(DestinasiEntryRev.route)
                },
                onDetailRevClick = { ID_REVIEW ->
                    navController.navigate("${DestinasiDetailRev.route}/$ID_REVIEW")
                }
            )
        }

        // Halaman Entry Review
        composable(DestinasiEntryRev.route) {
            EntryRevScreen(
                navigateBack = {
                    navController.navigate(DestinasiReview.route) {
                        popUpTo(DestinasiReview.route) { inclusive = true }
                    }
                }
            )
        }

        // Halaman Detail Review
        composable(
            DestinasiDetailRev.routeWithArg,
            arguments = listOf(navArgument(DestinasiDetailRev.ID_REVIEW) { type = NavType.StringType })
        ) { backStackEntry ->
            val idReview = backStackEntry.arguments?.getString(DestinasiDetailRev.ID_REVIEW)
            idReview?.let {
                DetailRevScreen(
                    navigateBack = {
                        navController.navigate(DestinasiReview.route) {
                            popUpTo(DestinasiReview.route) { inclusive = true }
                        }
                    },
                    navigateToEditRev = { idReview ->
                        navController.navigate("${DestinasiRevUpdate.route}/$ID_REVIEW")
                    }
                )
            }
        }

        // Halaman Update Review
        composable(
            DestinasiRevUpdate.routeWithArg,
            arguments = listOf(navArgument(DestinasiRevUpdate.ID_REVIEW) { type = NavType.StringType })
        ) { backStackEntry ->
            val idReview = backStackEntry.arguments?.getString(DestinasiRevUpdate.ID_REVIEW)
            idReview?.let {
                UpdateRevScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
    }
}