package com.velocip.ybs.network.fakes

import com.velocip.ybs.network.data_source.PhotoRemoteDataSource
import com.velocip.ybs.network.result.PhotoDetailsItem
import com.velocip.ybs.network.result.PhotoItem

class FakeRemoteDataSource(var photos: MutableList<PhotoItem>) : PhotoRemoteDataSource {

    private var shouldThrowError = false
    private val photoDetailsItem: MutableMap<String, PhotoDetailsItem> = mutableMapOf()


    override suspend fun getPhotos(query: String): Result<List<PhotoItem>> {
        return if (shouldThrowError) {
            Result.failure(Exception("getPhotos exception"))
        } else {
            Result.success(photos)
        }
    }

    override suspend fun getUserPhotos(userId: String): Result<List<PhotoItem>> {
        return if (shouldThrowError) {
            Result.failure(Exception("getUserPhotos exception"))
        } else {
            Result.success(photos)
        }
    }

    override suspend fun getPhotoDetails(photoId: String): Result<PhotoDetailsItem> {
        return if (shouldThrowError) {
            Result.failure(Exception("getPhotoDetails exception"))
        } else {
            val photoDetailsItem = this.photoDetailsItem[photoId]
            Result.success(photoDetailsItem!!)
        }
    }

    fun setShouldThrowError(value: Boolean) {
        shouldThrowError = value
    }

    fun setUpPhotoDetailsItem(photoId: String, photoDetailsItem: PhotoDetailsItem) {
        this.photoDetailsItem[photoId] = photoDetailsItem
    }
}