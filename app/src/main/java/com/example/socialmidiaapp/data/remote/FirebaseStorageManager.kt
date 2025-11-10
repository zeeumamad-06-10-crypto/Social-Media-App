package com.example.socialmidiaapp.data.remote

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirebaseStorageManager {

    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val storageRef: StorageReference = storage.reference.child("uploads")

    /**
     * Upload a file to Firebase Storage
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
        val fileRef = storageRef.child(fileName)

        fileRef.putFile(uri)
            .addOnSuccessListener {
                fileRef.downloadUrl
                    .addOnSuccessListener { url ->
                        onSuccess(url.toString())
                    }
                    .addOnFailureListener { exception ->
                        onFailure(exception)
                    }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}
