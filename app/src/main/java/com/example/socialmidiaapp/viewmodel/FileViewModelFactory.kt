package com.example.socialmidiaapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.socialmidiaapp.data.local.FileDao
import com.example.socialmidiaapp.data.remote.FirebaseStorageManager

class FileViewModelFactory(
    private val fileDao: FileDao,
    private val storageManager: FirebaseStorageManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FileViewModel(fileDao, storageManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}


