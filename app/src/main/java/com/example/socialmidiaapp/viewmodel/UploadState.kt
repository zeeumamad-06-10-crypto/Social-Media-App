package com.example.socialmidiaapp.viewmodel

sealed class UploadState {
    object Idle : UploadState()
    object Uploading : UploadState()
    data class Success(val message: String) : UploadState()
    data class Error(val error: String) : UploadState()
}
