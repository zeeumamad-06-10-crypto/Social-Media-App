package com.example.socialmidiaapp.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmidiaapp.data.local.FileDao
import com.example.socialmidiaapp.data.local.FileEntity

import com.example.socialmidiaapp.data.remote.FirebaseStorageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FileViewModel(
    private val fileDao: FileDao,
    private val storageManager: FirebaseStorageManager
) : ViewModel() {

    private val _uploadState = MutableStateFlow<UploadState>(UploadState.Idle)
    val uploadState: StateFlow<UploadState> = _uploadState

    private val _downloadState = MutableStateFlow<DownloadState>(DownloadState.Idle)
    val downloadState: StateFlow<DownloadState> = _downloadState

    // Upload a file to Firebase Storage
    fun uploadFile(uri: Uri, fileName: String, onComplete: () -> Unit = {}) {
        _uploadState.value = UploadState.Uploading

        storageManager.uploadFile(uri, fileName,
            onSuccess = { url ->
                viewModelScope.launch(Dispatchers.IO) {
                    // Save file info in Room
                    val entity = FileEntity(name = fileName, path = url)
                    fileDao.insertFile(entity)
                    _uploadState.value = UploadState.Success("File uploaded successfully!")
                    onComplete()
                }
            },
            onFailure = { exception ->
                _uploadState.value = UploadState.Error(exception.message ?: "Upload failed")
            }
        )
    }

    // Fetch all files from Room
    fun getAllFiles(onResult: (List<FileEntity>) -> Unit) {
        _downloadState.value = DownloadState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val files = fileDao.getAllFiles()
                _downloadState.value = DownloadState.Success(files)
                onResult(files)
            } catch (e: Exception) {
                _downloadState.value = DownloadState.Error(e.message ?: "Failed to fetch files")
            }
        }
    }
}
