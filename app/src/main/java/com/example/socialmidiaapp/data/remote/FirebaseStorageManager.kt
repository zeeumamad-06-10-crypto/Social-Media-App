package com.example.socialmidiaapp.data.remote

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await

class FirebaseStorageManager {

    // Initialize Firebase Storage instance
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val storageRef: StorageReference = storage.reference.child("uploads")

    /**
     * Upload a file to Firebase Storage (callback version)
     * @param uri Local file URI
     * @param fileName File name to save in Firebase
     * @param onSuccess Callback with download URL
     * @param onFailure Callback with Exception
     */
    fun uploadFile(
        uri: Uri,
        fileName: String,
        onSuccess: (downloadUrl: String) -> Unit,
        onFailure: (exception: Exception) -> Unit
    ) {
        val fileRef: StorageReference = storageRef.child(fileName)

        fileRef.putFile(uri)
            .addOnSuccessListener {
                fileRef.downloadUrl
                    .addOnSuccessListener { url: Uri ->
                        onSuccess(url.toString())
                    }
                    .addOnFailureListener { exception: Exception ->
                        onFailure(exception)
                    }
            }
            .addOnFailureListener { exception: Exception ->
                onFailure(exception)
            }
    }

    /**
     * Upload a file to Firebase Storage using coroutines
     * @param uri Local file URI
     * @param fileName File name to save in Firebase
     * @return Download URL string
     */
    suspend fun uploadFileAsync(uri: Uri, fileName: String): String {
        val fileRef: StorageReference = storageRef.child(fileName)
        return try {
            // Upload the file
            fileRef.putFile(uri).await()
            // Get the download URL
            fileRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            throw e
        }
    }
}
