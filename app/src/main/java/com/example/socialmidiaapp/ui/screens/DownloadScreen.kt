package com.example.socialmidiaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.socialmidiaapp.data.local.AppDatabase
import com.example.socialmidiaapp.data.remote.FirebaseStorageManager
import com.example.socialmidiaapp.viewmodel.FileViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadScreen(navController: NavController) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val dao = db.fileDao()
    val storageManager = FirebaseStorageManager()
    val viewModel = FileViewModel(dao, storageManager)
    val coroutineScope = rememberCoroutineScope()

    var fileList by remember { mutableStateOf(listOf<com.example.socialmidiaapp.data.local.FileEntity>()) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            viewModel.getAllFiles { files -> fileList = files }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Download Files") }) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                LazyColumn {
                    items(fileList) { file ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("Name: ${file.name}", style = MaterialTheme.typography.titleMedium)
                                Text("URL: ${file.path}", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                TextButton(onClick = { navController.navigate("home") }) {
                    Text("Back to Home")
                }
            }
        }
    )
}


