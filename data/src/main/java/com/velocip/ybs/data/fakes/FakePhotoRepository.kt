package com.velocip.ybs.data.fakes

import androidx.annotation.VisibleForTesting
import com.velocip.ybs.data.repo.PhotoSearchRepo
import com.velocip.ybs.model.PhotoDetails
import com.velocip.ybs.model.PhotoItemUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakePhotoRepository : PhotoSearchRepo {

    private var shouldThrowError = false

    private val _photos = MutableStateFlow(LinkedHashMap<String, PhotoItemUi>())
    val photos: StateFlow<LinkedHashMap<String, PhotoItemUi>> = _photos.asStateFlow()

    private val observablePhotos: Flow<List<PhotoItemUi>> = _photos.map {
        if (shouldThrowError) {
            throw Exception("Test exception")
        } else {
            it.values.toList()
        }
    }

    private val photosDetails = LinkedHashMap<String, PhotoDetails>()

    override suspend fun searchPhotos(query: String, refresh: Boolean): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun getUserPhotos(userId: String): Result<List<PhotoItemUi>> {
        observablePhotos.firstOrNull()?.let { photos ->
            return Result.success(photos.filter { it.userId == userId })
        } ?: return Result.failure(Exception("No photos found"))
    }

    override suspend fun getPhotosStream(query: String): Flow<List<PhotoItemUi>> = observablePhotos

    override suspend fun getPhotoDetails(photoId: String): Result<PhotoItemUi> {
        return photos.value[photoId]?.let { photo ->
            val photoDetails = photo.copy(
                details = photosDetails[photoId]!!
            )
            Result.success(photoDetails)
        } ?: Result.failure(Exception("Photo not found"))
    }


    fun setShouldThrowError(value: Boolean) {
        shouldThrowError = value
    }


    @VisibleForTesting
    fun addPhotos(vararg photos: PhotoItemUi) {
        _photos.update { oldPhotos ->
            val newPhotos = LinkedHashMap<String, PhotoItemUi>(oldPhotos)
            for (photo in photos) {
                newPhotos[photo.id] = photo
            }
            newPhotos
        }
    }

    @VisibleForTesting
    fun addPhotoDetails(id: String, photoDetails: PhotoDetails) {
        photosDetails[id] = photoDetails
    }
}