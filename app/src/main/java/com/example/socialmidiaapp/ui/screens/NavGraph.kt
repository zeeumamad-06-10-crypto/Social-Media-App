package com.example.socialmidiaapp.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.socialmidiaapp.viewmodel.FileViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    fileViewModel: FileViewModel
) {
    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            HomeScreen(navController = navController)
        }

        composable("upload") {
            UploadScreen(
                fileViewModel = fileViewModel,
                navController = navController
            )
        }

        composable("download") {
            DownloadScreen(
                fileViewModel = fileViewModel
            )
        }
    }
}
