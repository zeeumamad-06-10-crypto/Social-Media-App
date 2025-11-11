package com.example.socialmidiaapp.viewmodel

/**
 * Represents the current state of file uploads
 */
sealed class UploadState {
    object Idle : UploadState()                      // No operation happening
    object Uploading : UploadState()                 // Upload in progress
    data class Success(val message: String) : UploadState()          // Upload succeeded
    data class Error(val message: String) : UploadState()            // Upload failed
}
