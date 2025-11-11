package com.example.socialmidiaapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.socialmidiaapp.data.local.FileEntity
import com.example.socialmidiaapp.ui.components.FileCard
import com.example.socialmidiaapp.viewmodel.DownloadState
import com.example.socialmidiaapp.viewmodel.FileViewModel

@Composable
fun DownloadScreen(fileViewModel: FileViewModel) {
    val downloadState = fileViewModel.downloadState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = downloadState.value) {
            is DownloadState.Idle -> {
                Text(
                    text = "No files available",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is DownloadState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is DownloadState.Success -> {
                val files: List<FileEntity> = state.files
                if (files.isEmpty()) {
                    Text(
                        text = "No files uploaded yet",
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(files) { file ->
                            FileCard(file)
                        }
                    }
                }
            }
            is DownloadState.Error -> {
                Text(
                    text = "Error: ${state.message}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
