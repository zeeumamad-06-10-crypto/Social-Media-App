package com.example.socialmidiaapp.viewmodel

import com.example.socialmidiaapp.data.local.FileEntity


sealed class DownloadState {
    object Idle : DownloadState()
    object Loading : DownloadState()
    data class Success(val files: List<FileEntity>) : DownloadState()
    data class Error(val error: String) : DownloadState()
}
