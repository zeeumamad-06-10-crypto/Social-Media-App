package com.example.socialmidiaapp.data.repository

import android.net.Uri
import com.example.socialmidiaapp.data.local.FileEntity

interface FileRepository {
    suspend fun uploadFile(uri: Uri, fileName: String): String
    suspend fun insertFile(file: FileEntity)
    suspend fun getAllFiles(): List<FileEntity>
}
