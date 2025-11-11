package com.example.socialmidiaapp.viewmodel

import com.example.socialmidiaapp.data.local.FileEntity

/**
 * Represents the current state of file downloads
 */
sealed class DownloadState {

    /** No operation happening */
    object Idle : DownloadState()

    /** Download in progress */
    object Loading : DownloadState()

    /** Download completed successfully */
    data class Success(val files: List<FileEntity>) : DownloadState()

    /** Download failed */
    data class Error(val message: String) : DownloadState()
}
