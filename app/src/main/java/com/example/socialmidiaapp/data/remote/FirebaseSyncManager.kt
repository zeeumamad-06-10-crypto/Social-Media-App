package com.example.socialmidiaapp.data.remote

import com.example.socialmidiaapp.data.local.FileEntity
import com.google.firebase.firestore.FirebaseFirestore


class FirebaseSyncManager {

    private val firestore = FirebaseFirestore.getInstance()
    private val collectionRef = firestore.collection("files")

    /**
     * Save file metadata to Firestore
     */
    fun saveFileMetadata(file: FileEntity, onComplete: (Boolean, String?) -> Unit) {
        val data = hashMapOf(
            "name" to file.name,
            "path" to file.path
        )

        collectionRef.add(data)
            .addOnSuccessListener {
                // Upload successful
                onComplete(true, null)
            }
            .addOnFailureListener { exception ->
                // Use 'exception.message' instead of just 'message'
                onComplete(false, exception.message)
            }
    }
}
