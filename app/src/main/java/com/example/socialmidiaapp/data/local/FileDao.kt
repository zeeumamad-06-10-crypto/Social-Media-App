package com.example.socialmidiaapp.data.local



@Dao
interface FileDao {

    @Inser(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFile(file: FileEntity)

    @Query("SELECT * FROM files ORDER BY id DESC")
    suspend fun getAllFiles(): List<FileEntity>
}
