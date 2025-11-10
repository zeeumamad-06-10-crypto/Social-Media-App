package com.example.socialmidiaapp.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.socialmidiaapp.data.local.AppDatabase
import com.example.socialmidiaapp.data.remote.FirebaseStorageManager
import com.example.socialmidiaapp.viewmodel.FileViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadScreen(navController: NavController) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val dao = db.fileDao()
    val storageManager = FirebaseStorageManager()
    val viewModel = FileViewModel(dao, storageManager)
    val coroutineScope = rememberCoroutineScope()

    var selectedUri by remember { mutableStateOf<Uri?>(null) }
    var uploadStatus by remember { mutableStateOf("") }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> selectedUri = uri }
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("Upload File") }) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { filePickerLauncher.launch("*/*") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text("Choose File")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        selectedUri?.let { uri ->
                            uploadStatus = "Uploading..."
                            coroutineScope.launch {
                                viewModel.uploadFile(uri, "myfile_${System.currentTimeMillis()}") {
                                    uploadStatus = "Uploaded Successfully!"
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    enabled = selectedUri != null
                ) {
                    Text("Upload")
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(uploadStatus)

                Spacer(modifier = Modifier.height(20.dp))
                TextButton(onClick = { navController.navigate("home") }) {
                    Text("Back to Home")
                }
            }
        }
    )
}
