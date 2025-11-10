package com.example.socialmidiaapp.data.repository

import android.net.Uri
import com.example.socialmidiaapp.data.local.FileDao
import com.example.socialmidiaapp.data.local.FileEntity
import com.example.socialmidiaapp.data.remote.FirebaseStorageManager

class FileRepositoryImpl(
    private val fileDao: FileDao,
    private val storageManager: FirebaseStorageManager
) : FileRepository {

    override fun uploadFile(
        uri: Uri,
        fileName: String,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        storageManager.uploadFile(uri, fileName,
            onSuccess = { url -> onSuccess(url) },
            onFailure = { exception -> onFailure(exception) }
        )
    }

    override suspend fun insertFile(fileEntity: FileEntity) {
        fileDao.insertFile(fileEntity)
    }

    override suspend fun getAllFiles(): List<FileEntity> {
        return fileDao.getAllFiles()
    }
}
