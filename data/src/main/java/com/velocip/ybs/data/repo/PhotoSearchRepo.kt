package com.velocip.ybs.data.repo

import com.velocip.ybs.model.PhotoItemUi
import kotlinx.coroutines.flow.Flow


interface PhotoSearchRepo {

    /**
     * Searches for photos based on the provided query.
     *
     * This function performs a search for photos using the given query string. It first checks if the query
     * matches the most recent query and if the local photos are not expired. If the local photos are valid,
     * it returns a successful result. Otherwise, it fetches new photos from the remote data source, updates
     * the local database, and returns the result.
     *
     * @param query The search query string.
     * @return A [Result] object containing [Unit] if the search is successful, or an [Exception] if an error occurs.
     */
    suspend fun searchPhotos(query: String): Result<Unit>


    /**
     * Retrieves photos for a specific user.
     *
     * This function fetches photos associated with a given user ID. It first attempts to fetch
     * the photos from a remote data source and then maps them to a list of [PhotoItemUi] objects.
     *
     * @param userId The ID of the user whose photos are to be retrieved.
     * @return A [Result] object containing a list of [PhotoItemUi] objects or an error message.
     */
    suspend fun getUserPhotos(userId: String): Result<List<PhotoItemUi>>


    suspend fun getPhotos(): Flow<List<PhotoItemUi>>



    /**
     * Retrieves detailed information about a specific photo.
     *
     * This function fetches detailed information about a photo identified by its ID. It first
     * attempts to retrieve the photo details from the local database. If the details are not
     * available locally, it fetches them from a remote data source and updates the local cache.
     *
     * @param photoId The ID of the photo whose details are to be retrieved.
     * @return A [Result] object containing a [PhotoItemUi] object with detailed information or an error message.
     */
    suspend fun getPhotoDetails(photoId: String): Result<PhotoItemUi>
}