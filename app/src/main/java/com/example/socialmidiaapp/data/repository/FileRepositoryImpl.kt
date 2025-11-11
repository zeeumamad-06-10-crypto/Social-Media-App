package com.example.socialmidiaapp.data.repository

import android.net.Uri
import com.example.socialmidiaapp.data.local.FileDao
import com.example.socialmidiaapp.data.local.FileEntity
import com.example.socialmidiaapp.data.remote.FirebaseStorageManager

class FileRepositoryImpl(
    private val fileDao: FileDao,
    private val storageManager: FirebaseStorageManager
) : FileRepository {

    override suspend fun uploadFile(uri: Uri, fileName: String): String {
        // Upload to Firebase Storage
        val downloadUrl = storageManager.uploadFileAsync(uri, fileName)
        // Save to Room
        val entity = FileEntity(name = fileName, path = downloadUrl)
        fileDao.insertFile(entity)
        return downloadUrl
    }

    override suspend fun insertFile(file: FileEntity) {
        fileDao.insertFile(file)
    }

    override suspend fun getAllFiles(): List<FileEntity> {
        return fileDao.getAllFiles()
    }
}
