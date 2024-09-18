package com.velocip.ybs.data.fakes

import androidx.annotation.VisibleForTesting
import com.velocip.ybs.data.repo.PhotoSearchRepo
import com.velocip.ybs.model.PhotoDetails
import com.velocip.ybs.model.PhotoItemUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update


const val FAKE_PHOTO_NOT_FOUND_ERROR = "Photo details not found"
const val FAKE_GET_PHOTOS_ERROR = "No photos found for the user"

class FakePhotoRepository : PhotoSearchRepo {

    private var shouldThrowError = false

    private val _photos = MutableStateFlow(LinkedHashMap<String, PhotoItemUi>())
    val photos: StateFlow<LinkedHashMap<String, PhotoItemUi>> = _photos.asStateFlow()

    private val observablePhotos: Flow<List<PhotoItemUi>> = _photos.map {
        if (shouldThrowError) {
            emptyList()
        } else {
            it.values.toList()
        }
    }

    private val photosDetails = LinkedHashMap<String, PhotoDetails>()

    override suspend fun searchPhotos(query: String): Result<Unit> {
        return if (shouldThrowError) {
            Result.failure(Exception(FAKE_GET_PHOTOS_ERROR))
        } else {
            Result.success(Unit)
        }
    }


    override suspend fun getUserPhotos(userId: String): Result<List<PhotoItemUi>> {
        return if (shouldThrowError) {
            Result.failure(Exception(FAKE_GET_PHOTOS_ERROR))
        } else {
            Result.success(observablePhotos.first().filter { it.userId == userId })
        }
    }

    override suspend fun getPhotos(): Flow<List<PhotoItemUi>> = observablePhotos

    override suspend fun getPhotoDetails(photoId: String): Result<PhotoItemUi> {
        if (shouldThrowError) {
            return Result.failure(Exception(FAKE_PHOTO_NOT_FOUND_ERROR))
        } else {
            val photoDetails = photos.value[photoId]!!.copy(
                details = photosDetails[photoId] ?: PhotoDetails()
            )
            return Result.success(photoDetails)
        }
    }


    fun setShouldThrowError(value: Boolean) {
        shouldThrowError = value
    }


    @VisibleForTesting
    fun addPhotos(photos: List<PhotoItemUi>, replace: Boolean = false) {
        if (replace) {
            _photos.value.clear()
            _photos.value.putAll(photos.associateBy { it.id })
        } else {
            _photos.update { oldPhotos ->
                val newPhotos = LinkedHashMap<String, PhotoItemUi>(oldPhotos)
                for (photo in photos) {
                    newPhotos[photo.id] = photo
                }
                newPhotos
            }
        }
    }

    @VisibleForTesting
    fun addPhotoDetails(id: String, photoDetails: PhotoDetails) {
        photosDetails[id] = photoDetails
    }
}