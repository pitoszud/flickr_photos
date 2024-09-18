package com.velocip.ybs.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.velocip.ybs.database.entity.PhotoEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the photo table.
 */
@Dao
interface PhotoDao {

    /**
     * Observes all photos by query.
     * @return all photos from query as flow.
     */
    @Query("SELECT * FROM query_photos WHERE userQuery = :usrQuery")
    fun getPhotosByQuery(usrQuery: String): Flow<List<PhotoEntity>>


    /**
     * Observes all photos.
     * @return all photos as flow.
     */
    @Query("SELECT * FROM query_photos")
    fun getAllPhotos(): Flow<List<PhotoEntity>>


    /**
     * Deletes all photos.
     */
    @Query(value = "DELETE FROM query_photos")
    suspend fun deleteAll()


    /**
     * Insert or update photos in the database. If a photo already exists, replace it.
     * @param photos the photos to be inserted or updated.
     */
    @Upsert
    suspend fun upsertAll(photos: List<PhotoEntity>)


    /**
     * Select photo by id.
     * @param photoId the photo id.
     * @return the photo with photoId.
     */
    @Query("SELECT * FROM query_photos WHERE photoId = :photoId")
    suspend fun getPhoto(photoId: String): PhotoEntity?


    @Upsert
    suspend fun upsertPhoto(photoEntity: PhotoEntity)
}