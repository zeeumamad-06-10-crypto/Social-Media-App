package com.example.socialmidiaapp.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.socialmidiaapp.data.local.AppDatabase
import com.example.socialmidiaapp.data.remote.FirebaseStorageManager
import com.example.socialmidiaapp.viewmodel.FileViewModelFactory
import androidx.navigation.compose.rememberNavController
import com.example.socialmidiaapp.ui.theme.SocialMediaAppTheme
import com.example.socialmidiaapp.viewmodel.FileViewModel

class MainActivity : ComponentActivity() {

    // Provide dependencies via a ViewModel factory
    private val fileViewModel: FileViewModel by viewModels {
        val db = AppDatabase.getDatabase(applicationContext)
        val dao = db.fileDao()
        val storage = FirebaseStorageManager()
        FileViewModelFactory(dao, storage)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SocialMediaAppTheme {
                val navController = rememberNavController()
                AppNavGraph(
                    navController = navController,
                    fileViewModel = fileViewModel // pass ViewModel here
                )
            }
        }
    }
}
