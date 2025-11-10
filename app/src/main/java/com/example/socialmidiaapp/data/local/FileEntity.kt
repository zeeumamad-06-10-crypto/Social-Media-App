package com.example.socialmidiaapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "files")
data class FileEntity(
    val createdAt: Date = Date(),   // Timestamp for file creation

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,                // Primary key

    val name: String,               // File name
    val path: String                // File path or download URL
)
