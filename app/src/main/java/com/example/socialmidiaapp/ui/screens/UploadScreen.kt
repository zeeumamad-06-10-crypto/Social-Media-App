package com.example.socialmidiaapp.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.socialmidiaapp.ui.components.UploadButton
import com.example.socialmidiaapp.viewmodel.FileViewModel
import com.example.socialmidiaapp.viewmodel.UploadState
import coil.compose.AsyncImage
import android.provider.OpenableColumns

@Composable
fun UploadScreen(
    fileViewModel: FileViewModel,
    navController: NavHostController
) {
    var selectedUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher for selecting a file
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedUri = uri
    }

    // Permission launcher for reading images
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted: Boolean ->
        if (granted) {
            launcher.launch("image/*")
        }
    }

    val requiredPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val uploadState by fileViewModel.uploadState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Select file button
            UploadButton(text = "Select File") {
                val context = navController.context
                val hasPermission = ContextCompat.checkSelfPermission(
                    context,
                    requiredPermission
                ) == PackageManager.PERMISSION_GRANTED

                if (hasPermission) {
                    launcher.launch("image/*")
                } else {
                    permissionLauncher.launch(requiredPermission)
                }
            }

            // Preview selected image
            selectedUri?.let { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = "Selected image preview",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            // Upload file button, only enabled if a file is selected
            selectedUri?.let { uri ->
                UploadButton(text = "Upload File") {
                    // Resolve a safe file name from the Uri
                    val context = navController.context
                    val resolvedName = runCatching {
                        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
                            val idx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                            if (idx >= 0 && cursor.moveToFirst()) cursor.getString(idx) else null
                        }
                    }.getOrNull()

                    // Fallbacks and sanitization: keep only simple filename characters
                    val baseName = (resolvedName ?: uri.lastPathSegment ?: "image.jpg")
                    val sanitized = baseName.replace(Regex("[^A-Za-z0-9._-]"), "_")

                    // Prefix with timestamp to avoid collisions
                    val finalName = "${System.currentTimeMillis()}_$sanitized"

                    fileViewModel.uploadFile(uri, finalName)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Upload state feedback
            when (uploadState) {
                is UploadState.Uploading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                is UploadState.Success -> Text(
                    text = (uploadState as UploadState.Success).message,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                is UploadState.Error -> Text(
                    text = (uploadState as UploadState.Error).message,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                else -> {}
            }
        }
    }
}
